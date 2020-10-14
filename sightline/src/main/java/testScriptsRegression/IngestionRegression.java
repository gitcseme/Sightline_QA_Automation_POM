package testScriptsRegression;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

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

	@Test(groups = {"Ingestion", "Positive"})
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

	@Test(groups = { "Ingestion", "Positive"    })
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
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = { "Ingestion", "Positive"    })
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

	@Test(groups = { "Ingestion", "Positive"   })
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

	@Test(groups = { "Ingestion", "Positive"   })
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

	@Test(groups = { "Ingestion", "Positive"})
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

	@Test(groups = { "Ingestion", "Positive"   })
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

	@Test(groups = { "Ingestion", "Positive"    })
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

	@Test(groups = { "Ingestion", "Positive"  })
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
			dataMap.put("source_location", "IngestionTestData" + "\\" + "Automation");
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

	@Test(groups = { "Ingestion", "Positive" })
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
	
	@Test(groups = { "Ingestion", "Positive"})
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
			dataMap.put("source_location", "IngestionTestData" + "\\" + "Automation");
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

	@Test(groups = { "Ingestion", "Positive"})
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

	@Test(groups = { "Ingestion", "Positive" })
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

	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingest_execution_details_page_When_click_copy_button_Then_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingest_execution_details_page When click_copy_button Then verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.on_ingest_execution_details_page(true, dataMap);
			context.click_copy_button(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When add_a_new_ingestion_btn_is_clicked Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When add_a_new_ingestion_btn_is_clicked Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_add_a_new_ingestion_btn_is_clicked_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When add_a_new_ingestion_btn_is_clicked Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.add_a_new_ingestion_btn_is_clicked(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_click_copy_button_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When click_copy_button Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_copy_button(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_copy_button_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When click_copy_button Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_copy_button(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingest_execution_details_page_When_click_copy_button_Then_Not_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingest_execution_details_page When click_copy_button Then [Not] verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingest_execution_details_page(false, dataMap);
			context.click_copy_button(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_When_Then_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created When  Then click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AttachDocument");
			dataMap.put("audio_file", "AttachDocIDs2.dat");
			dataMap.put("mp3_file", "AttachDocIDs.dat");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocFileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_new_ingestion_created_Then_verify_source_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When new_ingestion_created Then verify_source_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_source_field_is_auto_populated(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_new_ingestion_created_Then_verify_destination_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When new_ingestion_created Then verify_destination_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_destination_field_is_auto_populated(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_verify_mandatory_toast_message_is_displayed_When_Then_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_mandatory_toast_message_is_displayed When  Then click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.verify_mandatory_toast_message_is_displayed(true, dataMap);

			context.click_preview_run_button(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_Then_Not_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When  Then [Not] click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);

			context.click_preview_run_button(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_Then_Not_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When  Then [Not] click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);

			context.click_preview_run_button(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_Then_Not_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When  Then [Not] click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);

			context.click_preview_run_button(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_Then_Not_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When  Then [Not] click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "Select");
			dataMap.put("source_location", "Select");
			dataMap.put("source_folder", "Select");
			dataMap.put("audio_file", "Select");
			dataMap.put("mp3_file", "Select");
			dataMap.put("date_time", "Select");
			dataMap.put("doc_key", "Select");
			dataMap.put("source_system", "Select");
			context.new_ingestion_created(false, dataMap);

			context.click_preview_run_button(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_new_ingestion_created_Then_Not_verify_source_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When new_ingestion_created Then [Not] verify_source_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_source_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_new_ingestion_created_Then_Not_verify_source_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When new_ingestion_created Then [Not] verify_source_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_source_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_new_ingestion_created_Then_Not_verify_source_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When new_ingestion_created Then [Not] verify_source_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_source_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_new_ingestion_created_Then_Not_verify_destination_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When new_ingestion_created Then [Not] verify_destination_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_destination_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_new_ingestion_created_Then_Not_verify_destination_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When new_ingestion_created Then [Not] verify_destination_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_destination_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_new_ingestion_created_Then_Not_verify_destination_field_is_auto_populated() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When new_ingestion_created Then [Not] verify_destination_field_is_auto_populated");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.new_ingestion_created(true, dataMap);
			context.verify_destination_field_is_auto_populated(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_verify_mandatory_toast_message_is_displayed_When_Then_Not_click_preview_run_button() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_mandatory_toast_message_is_displayed When  Then [Not] click_preview_run_button");

		dataMap.put("ExtentTest",test);

		try {
			context.verify_mandatory_toast_message_is_displayed(false, dataMap);

			context.click_preview_run_button(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"Ingestion", "Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and rename_MP3_doc_file When click_run_indexing_play_button Then verify_audio_indexing_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.rename_MP3_doc_file(true, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_Not_click_preview_run_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and [Not] click_preview_run_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(false, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_Not_click_preview_run_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and [Not] click_preview_run_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap); 
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_Not_click_preview_run_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and [Not] click_preview_run_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "AudioDocsTest");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "FileType");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	
	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_When_click_add_project_button_Then_verify_project_screen_displays_expected_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_When_click_add_project_button_Then_verify_project_screen_displays_expected_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_sau(true, dataMap);
			context.on_admin_home_page(true, dataMap);
			context.click_add_project_button(true, dataMap);
			context.verify_project_screen_displays_expected_options(true, dataMap);;
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_and_click_add_project_button_When_click_kick_off_help_icon_Then_verify_kick_off_analytics_help_option_displays_correct_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_and_click_add_project_button_When_click_kick_off_help_icon_Then_verify_kick_off_analytics_help_option_displays_correct_message");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_sau(true, dataMap);
			context.on_admin_home_page(true, dataMap);
			context.click_add_project_button(true, dataMap);
			context.click_kick_off_help_icon(true, dataMap);
			context.verify_kick_off_analytics_help_option_displays_correct_message(true, dataMap);	
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_and_click_add_project_button_When_click_run_incremental_help_icon_Then_verify_run_incremental_analytics_option_displays_correct_message () throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given_sightline_is_launched_and_login_as_sau_and_on_project_home_page_and_click_add_project_button_When_click_run_incremental_help_icon_Then_verify_run_incremental_analytics_option_displays_correct_message");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_sau(true, dataMap);
			context.on_admin_home_page(true, dataMap);
			context.click_add_project_button(true, dataMap);
			context.click_run_analytics_help_icon(true, dataMap);
			context.verify_run_incremental_analytics_option_displays_correct_message(true, dataMap);	
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Ingestion, Positive", "ignored"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_click_indexing_play_icon_When_start_analysing_step_Then_verify_ingestion_being_analysed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and click_indexing_play_icon When start_analysing_step Then verify_ingestion_being_analysed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_cataloged_filter(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			//context.start_analysing_step(true, dataMap);
			//context.verify_ingestion_being_analysed_can_not_be_searched(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_remove_mp3_attached_files_When_click_copy_play_icon_Then_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and remove_mp3_attached_files When click_copy_play_icon Then verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.remove_mp3_attached_files(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button When click_run_ingest_button Then verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_rename_mp3_doc_file_When_click_indexing_play_icon_Then_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and rename_mp3_doc_file When click_indexing_play_icon Then verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.rename_mp3_doc_file(true, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_click_indexing_play_icon_When_start_indexing_step_Then_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and click_indexing_play_icon When start_indexing_step Then verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_cataloged_filter(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_start_analysing_step_Then_Not_verify_ingestion_being_analysed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When start_analysing_step Then [Not] verify_ingestion_being_analysed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.start_analysing_step(true, dataMap);
			context.verify_ingestion_being_analysed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative", "ignore"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_start_analysing_step_Then_Not_verify_ingestion_being_analysed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When start_analysing_step Then [Not] verify_ingestion_being_analysed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.start_analysing_step(true, dataMap);
			context.verify_ingestion_being_analysed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_start_analysing_step_Then_Not_verify_ingestion_being_analysed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When start_analysing_step Then [Not] verify_ingestion_being_analysed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.start_analysing_step(true, dataMap);
			context.verify_ingestion_being_analysed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "Select");
			dataMap.put("source_location", "Select");
			dataMap.put("source_folder", "Select");
			dataMap.put("audio_file", "Select");
			dataMap.put("mp3_file", "Select");
			dataMap.put("date_time", "Select");
			dataMap.put("doc_key", "Select");
			dataMap.put("source_system", "Select");
			context.new_ingestion_created(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_map_configuration_fields_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] map_configuration_fields When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_Not_click_preview_run_button_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and [Not] click_preview_run_button When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_Not_click_run_ingest_button_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and [Not] click_run_ingest_button When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_Not_remove_mp3_attached_files_When_click_copy_play_icon_Then_Not_verify_copy_step_fails_for_mp3_ingestion() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and [Not] remove_mp3_attached_files When click_copy_play_icon Then [Not] verify_copy_step_fails_for_mp3_ingestion");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.remove_mp3_attached_files(false, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.verify_copy_step_fails_for_mp3_ingestion(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "Select");
			dataMap.put("source_location", "Select");
			dataMap.put("source_folder", "Select");
			dataMap.put("audio_file", "Select");
			dataMap.put("mp3_file", "Select");
			dataMap.put("date_time", "Select");
			dataMap.put("doc_key", "Select");
			dataMap.put("source_system", "Select");
			context.new_ingestion_created(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_map_configuration_fields_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] map_configuration_fields When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_ingestion_in_progress_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_ingestion_in_progress_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_in_progress_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "Select");
			dataMap.put("source_location", "Select");
			dataMap.put("source_folder", "Select");
			dataMap.put("audio_file", "Select");
			dataMap.put("mp3_file", "Select");
			dataMap.put("date_time", "Select");
			dataMap.put("doc_key", "Select");
			dataMap.put("source_system", "Select");
			context.new_ingestion_created(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_map_configuration_fields_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] map_configuration_fields When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_Not_click_preview_run_button_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and [Not] click_preview_run_button When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_Not_click_run_ingest_button_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and [Not] click_run_ingest_button When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_Not_click_copy_play_icon_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and [Not] click_copy_play_icon When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_Not_rename_mp3_doc_file_When_click_indexing_play_icon_Then_Not_verify_renaming_doc_fails_indexing_step() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and [Not] rename_mp3_doc_file When click_indexing_play_icon Then [Not] verify_renaming_doc_fails_indexing_step");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.rename_mp3_doc_file(false, dataMap);
			context.click_indexing_play_icon(true, dataMap);
			context.verify_renaming_doc_fails_indexing_step(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("native_file", "Select");
			dataMap.put("source_location", "Select");
			dataMap.put("source_folder", "Select");
			dataMap.put("audio_file", "Select");
			dataMap.put("mp3_file", "Select");
			dataMap.put("date_time", "Select");
			dataMap.put("doc_key", "Select");
			dataMap.put("source_system", "Select");
			context.new_ingestion_created(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_map_configuration_fields_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] map_configuration_fields When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_Not_click_preview_run_button_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and [Not] click_preview_run_button When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_Not_click_run_ingest_button_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and [Not] click_run_ingest_button When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_Not_click_copy_play_icon_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and [Not] click_copy_play_icon When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_icon_and_Not_click_indexing_play_icon_When_start_indexing_step_Then_Not_verify_ingestion_being_indexed_can_not_be_searched() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and click_copy_play_icon and [Not] click_indexing_play_icon When start_indexing_step Then [Not] verify_ingestion_being_indexed_can_not_be_searched");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "native.lst");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("audio_file", "Transcript.lst");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DocID");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_icon(true, dataMap);
			context.click_indexing_play_icon(false, dataMap);
			context.start_indexing_step(true, dataMap);
			context.verify_ingestion_being_indexed_can_not_be_searched(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_file_description_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_file_description_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
//			context.new_ingestion_created(true, dataMap);
//			context.map_configuration_fields(true, dataMap);
//			context.click_preview_run_button(true, dataMap);
//			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			context.verify_file_description_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_email_metadata_is_populated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();
		HashSet<String> targetColumns = new HashSet();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_email_metadata_is_populated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
//			
//			targetColumns.add("FAMILYID");
//			targetColumns.add("DOCDATE");
//			targetColumns.add("GUID");
//			targetColumns.add("RECORDTYPE");
//			targetColumns.add("EMAILSUBJECT");
//			targetColumns.add("SOURCEDOCID");
//			dataMap.put("targetColumns", targetColumns);

//			dataMap.put("source_system", "ICE"); 
//			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator); 
//			dataMap.put("source_folder", "SQA_Default_Automation"); 
//			dataMap.put("dat_load_file", "EmailData.dat"); 
//			//dataMap.put("native_checkbox", "true"); 
//			dataMap.put("text_file", "TextEmailData.lst");
//			//dataMap.put("dat_load_file", "EmailData.dat"); 
//			//dataMap.put("text_checkbox", "true"); 
//			dataMap.put("native_file", "NativeEmailData.LST"); 
//			dataMap.put("date_time", "MM/DD/YYYY"); 
//			dataMap.put("doc_key", "DocID"); 
			

			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			//context.click_run_ingest_button(true, dataMap);
			
			dataMap.put("actionNavigateDoc", "docView");
			context.on_search_home_page(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.verify_email_metadata_is_populated_correctly(true, dataMap);
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


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_expected_fields_are_in_data_set() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_expected_fields_are_in_data_set");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");

			/*
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			*/

			context.on_search_home_page(true, dataMap);
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			context.verify_expected_fields_are_in_data_set(true, dataMap);
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


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_processing_ocr_completed_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_processing_ocr_completed_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "DocBasic");
			context.search_for_ingestion(true, dataMap);
			context.verify_processing_ocr_completed_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_family_relationship_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_family_relationship_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);

			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "Family");
			context.search_for_ingestion(true, dataMap);
			context.verify_family_relationship_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_exception_resolution_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_exception_resolution_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "DocBasic");
			context.search_for_ingestion(true, dataMap);
			context.verify_exception_resolution_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_email_duplicated_doc_id_is_populated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_email_duplicated_doc_id_is_populated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.verify_email_duplicated_doc_id_is_populated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_excel_protected_workbook_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_excel_protected_workbook_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			/*
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			*/
			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "DocBasic");
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			context.verify_excel_protected_workbook_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_all_custodians_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_all_custodians_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			/*
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			*/
			context.on_search_home_page(true, dataMap);
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			dataMap.put("map_field", "DocBasic");
			dataMap.put("A", "");
			context.verify_all_custodians_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_review_export_id_is_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_review_export_id_is_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			/*
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			*/
			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "DocBasic");
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			context.verify_review_export_id_is_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_attach_doc_ids_are_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_attach_doc_ids_are_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
//			context.new_ingestion_created(true, dataMap);
//			context.map_configuration_fields(true, dataMap);
//			context.click_preview_run_button(true, dataMap);
//			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			dataMap.put("map_field", "Family");
			dataMap.put("actionNavigateDoc", "docView");
			context.search_for_ingestion(true, dataMap);
			context.verify_attach_doc_ids_are_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_map_configuration_fields_and_click_preview_run_button_and_click_run_ingest_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_hidden_properties_are_tally_searchable() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and map_configuration_fields and click_preview_run_button and click_run_ingest_button and on_search_home_page When search_for_ingestion Then verify_hidden_properties_are_tally_searchable");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.verify_hidden_properties_are_tally_searchable(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_save_button_and_on_search_home_page_When_search_for_ingestion_Then_verify_draft_ingestion_files_are_not_found() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_save_button and on_search_home_page When search_for_ingestion Then verify_draft_ingestion_files_are_not_found");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("text_file", "text.lst");
			dataMap.put("dat_load_file", "AttachDocIDs.dat");
			dataMap.put("text_checkbox", "true");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation"+File.separator);
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "SourceDocID");
			dataMap.put("source_system", "NUIX");
			dataMap.put("ingestQuery", "0C8A_SQA_Default_Automation_20201008214417943");
			context.new_ingestion_created(true, dataMap);
			context.click_save_button(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.verify_draft_ingestion_files_are_not_found(true, dataMap);
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
	
	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_login_as_pau_When_click_run_ingest_button_Then_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and login_as_pau When click_run_ingest_button Then verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("source_system", "NUIX");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("date_time", "MM/DD/YYY");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_filter_by_dropdown(true, dataMap);
			context.verify_components_are_displayed_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_saved_draft_ingestion_and_click_open_wizard_option_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_user_can_ingest_a_saved_draft() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_saved_draft_ingestion and click_open_wizard_option and click_preview_run_button When click_run_ingest_button Then verify_user_can_ingest_a_saved_draft");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("source_system", "NUIX");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("date_time", "MM/DD/YYY");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.on_saved_draft_ingestion(true, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.click_next_button(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_user_can_ingest_a_saved_draft(true, dataMap);
		} catch (ImplementationException e) {
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


	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_click_filter_by_dropdown_Then_verify_filter_by_dropdown_displays_expected_options() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When click_filter_by_dropdown Then verify_filter_by_dropdown_displays_expected_options");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			//dataMap.put("filter_option", "Draft");
			//dataMap.put("filter_option", "In Progress");
			//dataMap.put("filter_option", "Failed");
			//dataMap.put("filter_option", "Cataloged");
			dataMap.put("filter_option", "Copied");
			//dataMap.put("filter_option", "Indexed");
			//dataMap.put("filter_option", "Approved");
			dataMap.put("filter_option", "Published");
			context.click_filter_by_dropdown(true, dataMap, 8);
			context.verify_filter_by_dropdown_displays_expected_options(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_ingestion_home_page_Then_verify_view_options_are_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_ingestion_home_page Then verify_view_options_are_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_view_options_are_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_click_filter_by_dropdown_Then_verify_filter_by_dropdown_has_default_option_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When click_filter_by_dropdown Then verify_filter_by_dropdown_has_default_option_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_filter_by_dropdown_has_default_option_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_on_rollback_option_When_click_delete_option_Then_verify_rolled_back_ingestion_can_be_deleted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_on_rollback_option When click_delete_option Then verify_rolled_back_ingestion_can_be_deleted");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_ingestion_title(true, dataMap);						
			dataMap.put("filter_option", "Cataloged");
			context.click_filter_by_dropdown(true, dataMap);
			context.click_on_rollback_option(true, dataMap);
			dataMap.put("filter_option", "Draft");
			context.click_filter_by_dropdown(true,  dataMap);
			context.click_grid_view(true, dataMap);
			context.delete_grid_ingestion(true, dataMap);
			context.verify_rolled_back_ingestion_can_be_deleted(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive", "other"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_grid_view_displays_expected_fields() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button When click_run_ingest_button Then verify_ingestion_grid_view_displays_expected_fields");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_grid_view_displays_expected_fields(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_When_on_ingestion_home_page_Then_verify_ingestion_home_page_displays_default_tile_count() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau When on_ingestion_home_page Then verify_ingestion_home_page_displays_default_tile_count");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_ingestion_home_page_displays_default_tile_count(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_user_can_ingest_a_saved_draft_When_Then_verify_ingestion_home_page_is_refreshed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_user_can_ingest_a_saved_draft When Then verify_ingestion_home_page_is_refreshed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			dataMap.put("source_system", "NUIX");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("date_time", "MM/DD/YYY");
			context.on_saved_draft_ingestion(true, dataMap);
			context.click_open_wizard_option(true, dataMap);
			context.click_next_button(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_user_can_ingest_a_saved_draft(true, dataMap);
			context.verify_ingestion_home_page_is_refreshed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
			e.printStackTrace();
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_Not_on_ingestion_home_page_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and [Not] on_ingestion_home_page and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_Not_on_ingestion_home_page_and_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and [Not] on_ingestion_home_page and click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and on_ingestion_home_page and [Not] new_ingestion_created and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(false, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.new_ingestion_created(false, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_Not_sightline_is_launched_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_components_are_displayed_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and on_ingestion_home_page and new_ingestion_created and click_preview_run_button When click_run_ingest_button Then [Not] verify_components_are_displayed_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("pdf_checkbox", "true");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_components_are_displayed_correctly(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_select_all_filter_by_options_and_select_sort_by_LastModifiedDate_option_When_Then_verify_sort_by_works_as_expected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and select_all_filter_by_options and select_sort_by_{LastModifiedDate}_option When  Then verify_sort_by_works_as_expected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.select_all_filter_by_options(true, dataMap);
			dataMap.put("sort_by_options", "Last Modified Date");
			context.select_sort_by_option(true, dataMap);
			context.verify_sort_by_works_as_expected(true, dataMap);
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

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_select_all_filter_by_options_and_select_sort_by_Status_option_When_Then_verify_sort_by_works_as_expected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and select_all_filter_by_options and select_sort_by_{Status}_option When  Then verify_sort_by_works_as_expected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.select_all_filter_by_options(true, dataMap);
			dataMap.put("sort_by_options", "Status");
			context.select_sort_by_option(true, dataMap);
			context.verify_sort_by_works_as_expected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_select_all_filter_by_options_and_select_sort_by_LastModifieduser_option_When_Then_verify_sort_by_works_as_expected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and select_all_filter_by_options and select_sort_by_{LastModifieduser}_option When  Then verify_sort_by_works_as_expected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.select_all_filter_by_options(true, dataMap);
			dataMap.put("sort_by_options", "Last Modified user");
			context.select_sort_by_option(true, dataMap);
			context.verify_sort_by_works_as_expected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_select_all_filter_by_options_and_select_sort_by_ProjectName_option_When_Then_verify_sort_by_works_as_expected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and select_all_filter_by_options and select_sort_by_{ProjectName}_option When  Then verify_sort_by_works_as_expected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.select_all_filter_by_options(true, dataMap);
			dataMap.put("sort_by_options", "Project Name");
			context.select_sort_by_option(true, dataMap);
			context.verify_sort_by_works_as_expected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_view_options_are_displayed_When_click_grid_view_Then_verify_pagination_exists_on_grid_view() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_view_options_are_displayed When click_grid_view Then verify_pagination_exists_on_grid_view");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_view_options_are_displayed(true, dataMap);
			context.click_grid_view(true, dataMap);
			context.verify_pagination_exists_on_grid_view(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_ingestion_home_page_displays_default_tile_count_When_scroll_click_load_more_button_Then_verify_load_more_button_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_ingestion_home_page_displays_default_tile_count When scroll_click_load_more_button Then verify_load_more_button_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_ingestion_home_page_displays_default_tile_count(true, dataMap);
			context.scroll_click_load_more_button(true, dataMap);
			context.verify_load_more_button_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_load_more_button_is_displayed_When_scroll_click_load_more_button_Then_verify_load_more_button_disappears() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_load_more_button_is_displayed When scroll_click_load_more_button Then verify_load_more_button_disappears");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.verify_ingestion_home_page_displays_default_tile_count(true, dataMap);
			context.scroll_click_load_more_button(true, dataMap);
			context.verify_load_more_button_is_displayed(true, dataMap);
			context.scroll_click_load_more_button(true, dataMap);
			context.verify_load_more_button_disappears(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_When_click_copy_option_Then_verify_copy_ingestion_does_not_display_warning_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and new_ingestion_created and click_preview_run_button and click_run_ingest_button When click_copy_option Then verify_copy_ingestion_does_not_display_warning_message");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_option(true, dataMap);
			context.verify_copy_ingestion_does_not_display_warning_message(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_new_ingestion_created_and_login_as_pau_and_on_ingestion_home_page_When_click_back_button_Then_verify_back_button_works_as_expected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and new_ingestion_created and login_as_pau and on_ingestion_home_page When click_back_button Then verify_back_button_works_as_expected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "NUIX");			
			context.new_ingestion_created(true, dataMap);
			context.click_back_button(true, dataMap);
			context.verify_back_button_works_as_expected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_When_click_copy_option_Then_verify_copy_ingestion_displays_warning_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and new_ingestion_created and click_preview_run_button and click_run_ingest_button When click_copy_option Then verify_copy_ingestion_displays_warning_message");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_option(true, dataMap);
			context.verify_copy_ingestion_displays_warning_message(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_select_valid_email_metadata_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_with_email_metadata_is_published() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and select_valid_email_metadata and click_preview_run_button When click_run_ingest_button Then verify_ingestion_with_email_metadata_is_published");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "DAT4_STC_newdateformatEmailData - Copy.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "GD_994_Native_Text_ForProduction");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "EmailCCNameAndCCAddress");
			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.select_valid_email_metadata(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_with_email_metadata_is_published(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_datasets_home_page_and_search_for_dataset_When_on_doc_view_Then_verify_concatenated_values_are_displayed_correctly_in_the_email() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_datasets_home_page and search_for_dataset When on_doc_view Then verify_concatenated_values_are_displayed_correctly_in_the_email");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_datasets_home_page(true, dataMap);
			dataMap.put("dataset_name", "Email Set");
			context.search_for_dataset(true, dataMap);
			context.on_doc_view(true, dataMap);
			context.verify_concatenated_values_are_displayed_correctly_in_the_email(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_When_select_valid_email_metadata_Then_verify_valid_email_metadata_option_is_available() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created When select_valid_email_metadata Then verify_valid_email_metadata_option_is_available");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "GD_994_Native_Text_ForProduction");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.select_valid_email_metadata(true, dataMap);
			context.verify_valid_email_metadata_option_is_available(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_deselect_all_mandatory_fields_When_click_next_button_Then_verify_mandatory_error_message_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_add_a_new_ingestion_btn_is_clicked_and_deselect_all_mandatory_fields_When_click_next_button_Then_verify_mandatory_error_message_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "NUIX");
			context.new_ingestion_created(true, dataMap);
			context.map_configuration_fields(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_option(true, dataMap);
			context.verify_copy_ingestion_displays_warning_message(true, dataMap);
			context.verify_mandatory_error_message_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_source_system_ingestion_overlay_fails_When_ignore_errors_found_Then_verify_ignoring_errors_still_ingests_remaining_files() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_source_system_ingestion_overlay_fails When ignore_errors_found Then verify_ignoring_errors_still_ingests_remaining_files");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "Mapped Data");
			context.complete_overlay_ingestion(true, dataMap);
			context.verify_source_system_ingestion_overlay_fails(true, dataMap);
			context.ignore_errors_found(true, dataMap);
			context.verify_ignoring_errors_still_ingests_remaining_files(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_tally_view_displays_doc_primary_language_metadata_correctly_When_on_sub_tally_view_Then_verify_sub_tally_view_displays_doc_primary_language_metadata_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_tally_view_displays_doc_primary_language_metadata_correctly When on_sub_tally_view Then verify_sub_tally_view_displays_doc_primary_language_metadata_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_existing_ingestion(true, dataMap);
			context.on_doc_list_view(true, dataMap);
			context.verify_doc_list_displays_doc_primary_language_metadata_correctly(true, dataMap);
			context.on_tally_view(true, dataMap);
			context.verify_tally_view_displays_doc_primary_language_metadata_correctly(true, dataMap);
			context.on_sub_tally_view(true, dataMap);
			context.verify_sub_tally_view_displays_doc_primary_language_metadata_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_When_click_run_ingest_button_Then_verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created When click_run_ingest_button Then verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_search_home_page_and_search_for_existing_ingestion_When_on_doc_list_view_Then_verify_doc_list_displays_doc_primary_language_metadata_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_search_home_page and search_for_existing_ingestion When on_doc_list_view Then verify_doc_list_displays_doc_primary_language_metadata_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_existing_ingestion(true, dataMap);
			context.on_doc_list_view(true, dataMap);
			context.verify_doc_list_displays_doc_primary_language_metadata_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_doc_list_displays_doc_primary_language_metadata_correctly_When_on_doc_view_Then_verify_doc_view_displays_doc_primary_language_metadata_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_doc_list_displays_doc_primary_language_metadata_correctly When on_doc_view Then verify_doc_view_displays_doc_primary_language_metadata_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_existing_ingestion(true, dataMap);
			context.on_doc_list_view(true, dataMap);
			context.verify_doc_list_displays_doc_primary_language_metadata_correctly(true, dataMap);
			context.on_doc_view(true, dataMap);
			context.verify_doc_view_displays_doc_primary_language_metadata_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_source_system_ingestion_overlay_fails_and_click_on_rollback_option_and_open_saved_draft_ingestion_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_re_running_ingestion_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_source_system_ingestion_overlay_fails and click_on_rollback_option and open_saved_draft_ingestion and click_preview_run_button When click_run_ingest_button Then verify_re_running_ingestion_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "Mapped Data");
			context.complete_overlay_ingestion(true, dataMap);
			context.verify_source_system_ingestion_overlay_fails(true, dataMap);
			context.click_on_rollback_option(true, dataMap);
			context.open_saved_draft_ingestion(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_re_running_ingestion_is_successful(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_doc_list_displays_doc_primary_language_metadata_correctly_When_on_tally_view_Then_verify_tally_view_displays_doc_primary_language_metadata_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_doc_list_displays_doc_primary_language_metadata_correctly When on_tally_view Then verify_tally_view_displays_doc_primary_language_metadata_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_search_home_page(true, dataMap);
			context.search_for_existing_ingestion(true, dataMap);
			context.on_doc_list_view(true, dataMap);
			context.verify_doc_list_displays_doc_primary_language_metadata_correctly(true, dataMap);
			context.on_tally_view(true, dataMap);
			context.verify_tally_view_displays_doc_primary_language_metadata_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay_When_complete_overlay_ingestion_Then_verify_source_system_ingestion_overlay_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay When complete_overlay_ingestion Then verify_source_system_ingestion_overlay_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_overlay_is_sucessfuly_for_source_parent_doc_id_overlay(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "Mapped Data");
			context.complete_overlay_ingestion(true, dataMap);
			context.verify_source_system_ingestion_overlay_fails(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_on_ingestion_home_page_and_complete_overlay_ingestion_and_publish_ingested_files_and_search_for_ingestion_When_on_doc_view_Then_verify_overlay_ingestion_metadata_is_correct() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and on_ingestion_home_page and complete_overlay_ingestion and publish_ingested_files and search_for_ingestion When on_doc_view Then verify_overlay_ingestion_metadata_is_correct");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.complete_overlay_ingestion(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.on_doc_view(true, dataMap);
			context.verify_overlay_ingestion_metadata_is_correct(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_ingestion_overlay_is_ingested_successfully_When_complete_overlay_ingestion_Then_verify_overlay_with_different_files_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_ingestion_overlay_is_ingested_successfully When complete_overlay_ingestion Then verify_overlay_with_different_files_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "DAT4_STC_Natives.lst");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "1");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "PDF.LST");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.complete_overlay_ingestion(true, dataMap);
			context.verify_overlay_with_different_files_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_1_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{1} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "DAT4_STC_Natives.lst");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "1");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_2_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{2} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "2");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_3_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{3} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "3");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "tiff.lst");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_4_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{4} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "4");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "text.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_5_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{5} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "MP3.lst");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "5");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_6_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_ingestion_overlay_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created_{6} and click_preview_run_button When click_run_ingest_button Then verify_ingestion_overlay_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "DAT4_STC_Text.lst");
			dataMap.put("native_file", "");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "6");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_on_ingestion_home_page_and_complete_overlay_ingestion_When_open_ingestion_details_page_Then_verify_source_system_error_message_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and publish_ingested_files and on_ingestion_home_page and complete_overlay_ingestion When open_ingestion_details_page Then verify_source_system_error_message_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.complete_overlay_ingestion(true, dataMap);
			context.open_ingestion_details_page(true, dataMap);
			context.verify_source_system_error_message_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created2_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_search_for_ingestion_and_unrelease_ingested_documents_When_unpublish_ingestion_files_Then_verify_unpublish_overlay_ingestion_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created2 and click_preview_run_button and click_run_ingest_button and publish_ingested_files and search_for_ingestion and unrelease_ingested_documents When unpublish_ingestion_files Then verify_unpublish_overlay_ingestion_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("pdf_file", "PDF.LST");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created2(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.search_for_ingestion(true, dataMap);
			context.unrelease_ingested_documents(true, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_overlay_ingestion_is_successful(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_ingestion_overlay_is_ingested_successfully_When_complete_overlay_ingestion_Then_verify_overlay_with_same_files_is_ingested_successfully() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_ingestion_overlay_is_ingested_successfully When complete_overlay_ingestion Then verify_overlay_with_same_files_is_ingested_successfully");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("native_file", "DAT4_STC_Natives.lst");
			dataMap.put("pdf_file", "");
			dataMap.put("audio_file", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("ingestionindex", "1");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("mp3_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_ingestion_overlay_is_ingested_successfully(true, dataMap);
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("native_file", "Natives.lst");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.complete_overlay_ingestion(true, dataMap);
			context.verify_overlay_with_same_files_is_ingested_successfully(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_new_ingestion_created_7_When_search_for_ingested_docs_Then_verify_previous_documents_are_updated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and new_ingestion_created_{7} When search_for_ingested_docs Then verify_previous_documents_are_updated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("pdf_file", "");
			dataMap.put("native_file", "");
			dataMap.put("other_link_type", "");
			dataMap.put("dat_load_file", "loadfile_copy.dat");
			dataMap.put("ingestionindex", "7");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_previous_documents_are_updated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_new_ingestion_created_8_When_search_for_ingested_docs_Then_verify_previous_documents_are_updated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and new_ingestion_created_{8} When search_for_ingested_docs Then verify_previous_documents_are_updated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("pdf_file", "PDF_copy.LST");
			dataMap.put("native_file", "");
			dataMap.put("other_link_type", "");
			dataMap.put("dat_load_file", "loadfile_copy.dat");
			dataMap.put("ingestionindex", "8");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_previous_documents_are_updated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_new_ingestion_created_9_When_search_for_ingested_docs_Then_verify_previous_documents_are_updated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and new_ingestion_created_{9} When search_for_ingested_docs Then verify_previous_documents_are_updated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("pdf_file", "");
			dataMap.put("native_file", "native_copy.lst");
			dataMap.put("other_link_type", "");
			dataMap.put("dat_load_file", "loadfile_copy.dat");
			dataMap.put("ingestionindex", "9");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_previous_documents_are_updated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_new_ingestion_created_10_When_search_for_ingested_docs_Then_verify_previous_documents_are_updated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and new_ingestion_created_{10} When search_for_ingested_docs Then verify_previous_documents_are_updated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			dataMap.put("other_load_file", "");
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("pdf_file", "");
			dataMap.put("native_file", "");
			dataMap.put("other_link_type", "");
			dataMap.put("dat_load_file", "loadfile_copy.dat");
			dataMap.put("ingestionindex", "10");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "tiff_copy.img");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_previous_documents_are_updated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created3_and_click_preview_run_button_and_click_run_ingest_button_and_new_ingestion_created_11_When_search_for_ingested_docs_Then_verify_previous_documents_are_updated_correctly() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created3 and click_preview_run_button and click_run_ingest_button and new_ingestion_created_{11} When search_for_ingested_docs Then verify_previous_documents_are_updated_correctly");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("ingestion_type", "Add Only");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created3(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			dataMap.put("other_load_file", "translation_copy.lst");
			dataMap.put("ingestion_type", "Overlay Only");
			dataMap.put("pdf_file", "");
			dataMap.put("native_file", "");
			dataMap.put("other_link_type", "Translation");
			dataMap.put("dat_load_file", "loadfile_copy.dat");
			dataMap.put("ingestionindex", "11");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("tiff_file", "");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created_(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_previous_documents_are_updated_correctly(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_When_search_for_ingested_docs_Then_verify_ingested_docs_are_in_sequential_order() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and publish_ingested_files When search_for_ingested_docs Then verify_ingested_docs_are_in_sequential_order");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.search_for_ingested_docs(true, dataMap);
			context.verify_ingested_docs_are_in_sequential_order(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	

	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_new_ingestion_created_Then_verify_preview_mapping_section_is_enabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When new_ingestion_created Then verify_preview_mapping_section_is_enabled");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("dat_load_file", "loadfile.dat");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("source_location", "IngestionTestData\Automation");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("doc_key", "DOCID");
			dataMap.put("source_system", "ICE");
			context.new_ingestion_created(true, dataMap);
			context.verify_preview_mapping_section_is_enabled(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_verify_mandatory_toast_message_is_displayed_When_click_preview_and_run_button_Then_verify_preview_mapping_section_is_enabled() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_mandatory_toast_message_is_displayed When click_preview_and_run_button Then verify_preview_mapping_section_is_enabled");

		dataMap.put("ExtentTest",test);

		try {
			context.verify_mandatory_toast_message_is_displayed(true, dataMap);
			context.click_preview_and_run_button(true, dataMap);
			context.verify_preview_mapping_section_is_enabled(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_search_for_existing_ingestion_and_open_ingestion_details_page_When_click_copy_button_Then_verify_multi_value_ascii_is_set_by_default() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and search_for_existing_ingestion and open_ingestion_details_page When click_copy_button Then verify_multi_value_ascii_is_set_by_default");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.search_for_existing_ingestion(true, dataMap);
			context.open_ingestion_details_page(true, dataMap);
			context.click_copy_button(true, dataMap);
			context.verify_multi_value_ascii_is_set_by_default(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
} //end

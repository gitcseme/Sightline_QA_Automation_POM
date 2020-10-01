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

	@Test(groups = { "Ingestion", "Positive"})
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("source_system", "NUIX");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("dat_file", "loadfile.dat");
			dataMap.put("doc_key", "DOCID"); 
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			
//			dataMap.put("native_file", "native.lst");
//			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
//			dataMap.put("source_folder", "AudioDocsTest");
//			dataMap.put("audio_file", "Transcript.lst");
//			dataMap.put("mp3_file", "MP3.lst");
//			dataMap.put("date_time", "MM/DD/YYY");
//			dataMap.put("doc_key", "FileType");
//			dataMap.put("source_system", "TRUE");
			context.new_ingestion_created(true, dataMap);
			context.click_source_DAT_field(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			//Removed for now, until button is fixed by Consilio
			//context.publish_ingested_files(true, dataMap);
			context.create_saved_search(true, dataMap);
			context.unpublish_ingestion_files(true, dataMap);
			context.verify_unpublish_for_audio_documents_is_successful(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Ingestion", "Positive", "smoke"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_When_select_audio_indexing_Then_verify_no_error_message_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page When select_audio_indexing Then verify_no_error_message_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			dataMap.put("source_system", "NUIX");
			dataMap.put("source_location", "IngestionTestData"+File.separator+"Automation");
			dataMap.put("source_folder", "SQA_Default_Automation");
			dataMap.put("mp3_file", "MP3.lst");
			dataMap.put("date_time", "MM/DD/YYY");
			dataMap.put("dat_file", "loadfile.dat");
			dataMap.put("doc_key", "DOCID");
			context.new_ingestion_created(true, dataMap);
			context.click_source_DAT_field(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			//context.click_catalog_play_button(true, dataMap);
			//context.click_copy_play_button(true, dataMap);
			
			//I was working on Select Audio Indexing.
			context.select_audio_indexing(true, dataMap);
			context.verify_no_error_message_is_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_click_preview_run_button_When_click_run_ingest_button_Then_verify_error_messaged_displays_mp3_variant() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and click_preview_run_button When click_run_ingest_button Then verify_error_messaged_displays_mp3_variant");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_error_messaged_displays_mp3_variant(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_When_run_ingestion_indexing_Then_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button When run_ingestion_indexing Then verify_document_and_audio_docs_count_are_the_same");

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
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_copy_play_button and rename_MP3_doc_file When click_run_indexing_play_button Then verify_audio_indexing_fails");

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
			context.click_copy_play_button(true, dataMap);
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


	@Test(groups = {"Ingestion, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_Not_click_preview_run_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and [Not] click_preview_run_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_Not_click_preview_run_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and [Not] click_preview_run_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.new_ingestion_created(false, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_Not_click_preview_run_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and [Not] click_preview_run_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.new_ingestion_created(false, dataMap);
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_Not_click_preview_run_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and [Not] click_preview_run_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_Not_click_preview_run_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and [Not] click_preview_run_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_Not_click_preview_run_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and [Not] click_preview_run_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.publish_ingested_files(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_run_ingest_button(false, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_and_publish_ingested_files_and_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button and publish_ingested_files and create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.click_run_ingest_button(false, dataMap);
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(true, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_publish_ingested_files_and_Not_create_saved_search_When_unpublish_ingestion_files_Then_Not_verify_unpublish_for_audio_documents_is_successful() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and publish_ingested_files and [Not] create_saved_search When unpublish_ingestion_files Then [Not] verify_unpublish_for_audio_documents_is_successful");

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
			context.publish_ingested_files(true, dataMap);
			context.create_saved_search(false, dataMap);
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_When_select_audio_indexing_Then_Not_verify_no_error_message_is_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page When select_audio_indexing Then [Not] verify_no_error_message_is_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.select_audio_indexing(true, dataMap);
			context.verify_no_error_message_is_displayed(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_click_preview_run_button_When_click_run_ingest_button_Then_Not_verify_error_messaged_displays_mp3_variant() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] click_preview_run_button When click_run_ingest_button Then [Not] verify_error_messaged_displays_mp3_variant");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.click_preview_run_button(false, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.verify_error_messaged_displays_mp3_variant(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_Not_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and [Not] click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(false, dataMap);
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_ingestion_home_page(true, dataMap);
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_Not_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and [Not] click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_Not_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and [Not] click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_Not_click_run_ingest_button_When_run_ingestion_indexing_Then_Not_verify_document_and_audio_docs_count_are_the_same() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and [Not] click_run_ingest_button When run_ingestion_indexing Then [Not] verify_document_and_audio_docs_count_are_the_same");

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
			context.click_run_ingest_button(false, dataMap);
			context.run_ingestion_indexing(true, dataMap);
			context.verify_document_and_audio_docs_count_are_the_same(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_Not_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and click_run_ingest_button and click_copy_play_button and [Not] rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

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
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_sightline_is_launched_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched and click_preview_run_button and click_run_ingest_button and click_copy_play_button and rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

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
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_Not_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and click_run_ingest_button and click_copy_play_button and [Not] rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(false, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_Not_login_as_pau_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login_as_pau and click_preview_run_button and click_run_ingest_button and click_copy_play_button and rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_Not_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and click_run_ingest_button and click_copy_play_button and [Not] rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_button(false, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_Not_on_ingestion_home_page_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and [Not] on_ingestion_home_page and click_preview_run_button and click_run_ingest_button and click_copy_play_button and rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
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
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_button(true, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_Not_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_copy_play_button and [Not] rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_button(true, dataMap);
			context.rename_MP3_doc_file(false, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_Not_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and [Not] new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_copy_play_button and rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

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
			context.click_preview_run_button(true, dataMap);
			context.click_run_ingest_button(true, dataMap);
			context.click_copy_play_button(true, dataMap);
			context.rename_MP3_doc_file(true, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_ingestion_home_page_and_new_ingestion_created_and_click_preview_run_button_and_click_run_ingest_button_and_click_copy_play_button_and_Not_rename_MP3_doc_file_When_click_run_indexing_play_button_Then_Not_verify_audio_indexing_fails() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_ingestion_home_page and new_ingestion_created and click_preview_run_button and click_run_ingest_button and click_copy_play_button and [Not] rename_MP3_doc_file When click_run_indexing_play_button Then [Not] verify_audio_indexing_fails");

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
			context.click_copy_play_button(true, dataMap);
			context.rename_MP3_doc_file(false, dataMap);
			context.click_run_indexing_play_button(true, dataMap);
			context.verify_audio_indexing_fails(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	

}

package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.google.protobuf.Empty;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.DocViewContext;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class DocViewRegression extends RegressionBase {

	DocViewContext context = new DocViewContext();

	@AfterMethod
	public void afterMethod() {
		context.close_drivers();
	}

	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_audio_doc_view_and_click_grey_redact_tool_When_apply_audio_redaction_Then_verify_audio_redaction() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_audio_doc_view and click_grey_redact_tool When apply_audio_redaction Then verify_audio_redaction");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "sqa.consilio7@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("project", "05May Proj_LCN");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG M21");
			dataMap.put("domain", "Test");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_audio_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.apply_audio_redaction(true, dataMap);
			context.verify_audio_redaction(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_open_dev_tools_f12_and_add_redaction_to_page_without_saving_and_redaction_setup_for_user2_and_switch_to_user1_and_disconnect_from_internet_and_switch_to_user2_and_save_applied_redaction_and_switch_to_user1_and_connect_to_internet_When_save_applied_redaction_Then_verify_redactiontagged_in_documentredactions_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and open_dev_tools_f12 and add_redaction_to_page_without_saving and redaction_setup_for_user2 and switch_to_user1 and disconnect_from_internet and switch_to_user2 and save_applied_redaction and switch_to_user1 and connect_to_internet When save_applied_redaction Then verify_redactiontagged_in_documentredactions_db");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.add_redaction_to_page_without_saving(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.redaction_setup_for_user2(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.disconnect_from_internet(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.connect_to_internet(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.verify_redactiontagged_in_documentredactions_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_open_dev_tools_f12_and_add_redaction_to_page_without_saving_and_redaction_setup_for_user2_and_switch_to_user1_and_disconnect_from_internet_and_switch_to_user2_and_save_applied_redaction_and_switch_to_user1_and_connect_to_internet_When_save_applied_redaction_Then_verify_redaction_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and open_dev_tools_f12 and add_redaction_to_page_without_saving and redaction_setup_for_user2 and switch_to_user1 and disconnect_from_internet and switch_to_user2 and save_applied_redaction and switch_to_user1 and connect_to_internet When save_applied_redaction Then verify_redaction_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.add_redaction_to_page_without_saving(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.redaction_setup_for_user2(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.disconnect_from_internet(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.connect_to_internet(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.verify_redaction_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_open_dev_tools_f12_and_wait_20_minutes_and_click_rectangle_redaction_button_When_add_redaction_to_page_Then_verify_redaction_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and open_dev_tools_f12 and wait_{20}_minutes and click_rectangle_redaction_button When add_redaction_to_page Then verify_redaction_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			dataMap.put("time", "20");
			context.wait_minutes(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.verify_redaction_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_open_dev_tools_f12_and_wait_20_minutes_and_click_rectangle_redaction_button_When_add_redaction_to_page_Then_verify_redactiontagged_in_documentredactions_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and open_dev_tools_f12 and wait_{20}_minutes and click_rectangle_redaction_button When add_redaction_to_page Then verify_redactiontagged_in_documentredactions_db");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			dataMap.put("time", "20");
			context.wait_minutes(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.verify_redactiontagged_in_documentredactions_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_open_dev_tools_f12_and_add_redaction_to_page_without_saving_and_reconnect_to_internet_When_save_applied_redaction_Then_verify_redaction_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and open_dev_tools_f12 and add_redaction_to_page_without_saving and reconnect_to_internet When save_applied_redaction Then verify_redaction_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.add_redaction_to_page_without_saving(true, dataMap);
			context.reconnect_to_internet(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.verify_redaction_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_open_dev_tools_f12_and_add_redaction_to_page_without_saving_and_reconnect_to_internet_When_save_applied_redaction_Then_verify_redactiontagged_in_documentredactions_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and open_dev_tools_f12 and add_redaction_to_page_without_saving and reconnect_to_internet When save_applied_redaction Then verify_redactiontagged_in_documentredactions_db");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.add_redaction_to_page_without_saving(true, dataMap);
			context.reconnect_to_internet(true, dataMap);
			context.save_applied_redaction(true, dataMap);
			context.verify_redactiontagged_in_documentredactions_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_open_dev_tools_f12_and_click_rectangle_redaction_button_When_add_redaction_to_page_repeatedly_for_30_mins_Then_verify_redactiontagged_in_documentredactions_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and open_dev_tools_f12 and click_rectangle_redaction_button When add_redaction_to_page_repeatedly_for_30_mins Then verify_redactiontagged_in_documentredactions_db");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page_repeatedly_for_30_mins(true, dataMap);
			context.verify_redactiontagged_in_documentredactions_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_open_dev_tools_f12_and_click_rectangle_redaction_button_When_add_redaction_to_page_repeatedly_for_30_mins_Then_verify_redaction_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and open_dev_tools_f12 and click_rectangle_redaction_button When add_redaction_to_page_repeatedly_for_30_mins Then verify_redaction_displayed");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.open_dev_tools_f12(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page_repeatedly_for_30_mins(true, dataMap);
			context.verify_redaction_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_add_redaction_to_page_and_nav_to_page2_of_doc_and_add_redaction_to_page_and_click_grey_redact_tool_and_delete_redactions_from_both_pages_When_browse_all_history_on_docview_Then_verify_redactionuntagged_in_doc_history() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and add_redaction_to_page and nav_to_page2_of_doc and add_redaction_to_page and click_grey_redact_tool and delete_redactions_from_both_pages When browse_all_history_on_docview Then verify_redactionuntagged_in_doc_history");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.nav_to_page2_of_doc(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.delete_redactions_from_both_pages(true, dataMap);
			context.browse_all_history_on_docview(true, dataMap);
			context.verify_redactionuntagged_in_doc_history(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_add_redaction_to_page_and_nav_to_page2_of_doc_and_add_redaction_to_page_and_click_grey_redact_tool_When_delete_redactions_from_both_pages_Then_verify_redactionuntagged_in_documentredactions_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and add_redaction_to_page and nav_to_page2_of_doc and add_redaction_to_page and click_grey_redact_tool When delete_redactions_from_both_pages Then verify_redactionuntagged_in_documentredactions_db");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.nav_to_page2_of_doc(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.delete_redactions_from_both_pages(true, dataMap);
			context.verify_redactionuntagged_in_documentredactions_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_add_redaction_to_page_and_nav_to_page2_of_doc_and_add_redaction_to_page_and_click_grey_redact_tool_When_browse_all_history_on_docview_Then_verify_redactiontagged_in_doc_history() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and add_redaction_to_page and nav_to_page2_of_doc and add_redaction_to_page and click_grey_redact_tool When browse_all_history_on_docview Then verify_redactiontagged_in_doc_history");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.nav_to_page2_of_doc(true, dataMap);
			context.add_redaction_to_page(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.browse_all_history_on_docview(true, dataMap);
			context.verify_redactiontagged_in_doc_history(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_When_rectangle_redaction_applied_Then_verify_redaction_transparent() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button When rectangle_redaction_applied Then verify_redaction_transparent");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.rectangle_redaction_applied(true, dataMap);
			context.verify_redaction_transparent(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.click_grey_redact_tool(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_all_page_redaction_button_When_place_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_all_page_redaction_button When place_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_all_page_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "Pending"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_page_range_redaction_button_When_place_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_page_range_redaction_button When place_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_page_range_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_rectangle_redaction_applied_and_click_rectangle_redaction_button_When_click_rectangle_redaction_button_Then_verify_redaction_control_in_off_state() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and rectangle_redaction_applied and click_rectangle_redaction_button When click_rectangle_redaction_button Then verify_redaction_control_in_off_state");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.rectangle_redaction_applied(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.verify_redaction_control_in_off_state(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.click_grey_redact_tool(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_rectangle_redaction_applied_and_nav_to_other_doc_When_delete_redaction_with_keyboard_delete_key_Then_verify_redaction_not_deleted_with_keyboard() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and rectangle_redaction_applied and nav_to_other_doc When delete_redaction_with_keyboard_delete_key Then verify_redaction_not_deleted_with_keyboard");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.rectangle_redaction_applied(true, dataMap);
			context.nav_to_other_doc(true, dataMap);
			context.delete_redaction_with_keyboard_delete_key(true, dataMap);
			context.verify_redaction_not_deleted_with_keyboard(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_this_page_redaction_button_and_this_page_redaction_applied_and_nav_to_other_doc_and_nav_back_to_first_doc_When_delete_redaction_with_keyboard_delete_key_Then_verify_redaction_not_deleted_with_keyboard() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_this_page_redaction_button and this_page_redaction_applied and nav_to_other_doc and nav_back_to_first_doc When delete_redaction_with_keyboard_delete_key Then verify_redaction_not_deleted_with_keyboard");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_this_page_redaction_button(true, dataMap);
			context.this_page_redaction_applied(true, dataMap);
			context.nav_to_other_doc(true, dataMap);
			context.nav_back_to_first_doc(true, dataMap);
			context.delete_redaction_with_keyboard_delete_key(true, dataMap);
			context.verify_redaction_not_deleted_with_keyboard(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_and_rectangle_redaction_applied_and_click_grey_redact_tool_When_rectangle_redaction_deleted_Then_verify_rectangle_redaction_deleted() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button and rectangle_redaction_applied and click_grey_redact_tool When rectangle_redaction_deleted Then verify_rectangle_redaction_deleted");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.rectangle_redaction_applied(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.verify_rectangle_redaction_deleted(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "Pending"})
	public void test_Given_default_redaction_tag_does_not_exist_and_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_all_page_redaction_button_When_place_redaction_Then_verify_alternate_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given default_redaction_tag_does_not_exist and sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_all_page_redaction_button When place_redaction Then verify_alternate_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.default_redaction_tag_does_not_exist(true, dataMap);
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_all_page_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_alternate_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "Pending"})
	public void test_Given_default_redaction_tag_does_not_exist_and_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_page_range_redaction_button_When_place_redaction_Then_verify_alternate_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given default_redaction_tag_does_not_exist and sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_page_range_redaction_button When place_redaction Then verify_alternate_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.default_redaction_tag_does_not_exist(true, dataMap);
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_page_range_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_alternate_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_When_rectangle_redaction_applied_Then_verify_rectangle_redaction() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button When rectangle_redaction_applied Then verify_rectangle_redaction");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.rectangle_redaction_applied(true, dataMap);
			context.verify_rectangle_redaction(true, dataMap);
		} catch (ImplementationException e) {
			e.printStackTrace();
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.click_grey_redact_tool(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive","Pending"})
	public void test_Given_default_redaction_tag_does_not_exist_and_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_When_place_redaction_Then_verify_alternate_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given default_redaction_tag_does_not_exist and sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button When place_redaction Then verify_alternate_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau2@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.default_redaction_tag_does_not_exist(true, dataMap);
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_alternate_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive","Pending"})
	public void test_Given_default_redaction_tag_does_not_exist_and_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_this_page_redaction_button_When_place_redaction_Then_verify_alternate_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given default_redaction_tag_does_not_exist and sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_this_page_redaction_button When place_redaction Then verify_alternate_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.default_redaction_tag_does_not_exist(true, dataMap);
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_this_page_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_alternate_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 

			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_this_page_redaction_button_When_place_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_this_page_redaction_button When place_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG2");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_this_page_redaction_button(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 

			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_nav_to_other_doc_When_nav_back_to_first_doc_Then_verify_redactions_menu_remains_open() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and nav_to_other_doc When nav_back_to_first_doc Then verify_redactions_menu_remains_open");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.nav_to_other_doc(true, dataMap);
			context.nav_back_to_first_doc(true, dataMap);
			context.verify_redactions_menu_remains_open(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_click_grey_redact_tool_and_click_rectangle_redaction_button_When_place_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and click_grey_redact_tool and click_rectangle_redaction_button When place_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG2");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.place_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
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

	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_edit_redaction_trueSGSame1_When_save_redaction_edit_Then_verify_redaction_edited() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and edit_redaction_{true}{SGSame1} When save_redaction_edit Then verify_redaction_edited");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("uid", "qapau2@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.save_redaction_edit(true, dataMap);
			context.verify_redaction_edited(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_edit_redaction_falseSGSame1_When_save_redaction_edit_Then_verify_redaction_edited() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and edit_redaction_{false}{SGSame1} When save_redaction_edit Then verify_redaction_edited");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			dataMap.put("tag", "SGSame1");
			dataMap.put("dimensions", "false");
			context.edit_redaction_(true, dataMap);
			context.save_redaction_edit(true, dataMap);
			context.verify_redaction_edited(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_edit_redaction_falseSGSame2_When_save_redaction_edit_Then_verify_redaction_edited() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and edit_redaction_{false}{SGSame2} When save_redaction_edit Then verify_redaction_edited");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "false");
			context.edit_redaction_(true, dataMap);
			context.save_redaction_edit(true, dataMap);
			context.verify_redaction_edited(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_search_doc_view_and_edit_redaction_trueSGSame2_When_save_redaction_edit_Then_verify_redaction_edited() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_search_doc_view and edit_redaction_{true}{SGSame2} When save_redaction_edit Then verify_redaction_edited");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("uid", "qapau3@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.save_redaction_edit(true, dataMap);
			context.verify_redaction_edited(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "john2"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_and_apply_rectangle_redaction_When_select_docview_doc_ID00000006_Then_verify_redaction_propagation_in_exact_dupe() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} and apply_rectangle_redaction When select_docview_doc_{ID00000006} Then verify_redaction_propagation_in_exact_dupe");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			dataMap.put("A", "");
			context.select_docview_doc_(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			//dataMap.put("docid", "ID00000006");
			dataMap.put("A", "");
			context.select_docview_doc_(true, dataMap);
			context.verify_redaction_propagation_in_exact_dupe(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_DomainAdmin_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_this_page_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{DomainAdmin} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_this_page_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "");
			dataMap.put("impersonate", "Domain Administrator");
			dataMap.put("security_group", "");
			dataMap.put("domain", "de");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_this_page_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_ProjectAdmin_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_this_page_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{ProjectAdmin} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_this_page_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Project Administrator");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_this_page_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_ReviewManager_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_this_page_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{ReviewManager} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_this_page_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_this_page_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive" })
	public void test_Given_sightline_is_launched_and_login_as_DomainAdmin_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_rectangle_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{DomainAdmin} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_rectangle_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "");
			dataMap.put("impersonate", "Domain Administrator");
			dataMap.put("security_group", "");
			dataMap.put("domain", "de");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_ProjectAdmin_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_rectangle_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{ProjectAdmin} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_rectangle_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Project Administrator");
			dataMap.put("security_group", "");
			dataMap.put("domain", "Not a Domain");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_ReviewManager_and_change_role_to_Reviewer_and_on_saved_search_page_and_open_saved_search_doc_view_When_apply_rectangle_redaction_Then_verify_default_redaction_tag_selected() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_{ReviewManager} and change_role_to_{Reviewer} and on_saved_search_page and open_saved_search_doc_view When apply_rectangle_redaction Then verify_default_redaction_tag_selected");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_as_(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Reviewer");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.change_role_to_(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.verify_default_redaction_tag_selected(true, dataMap);
			
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

	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_highlight_tool_Then_verify_sub_redaction_highlight_options_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_highlight_tool Then verify_sub_redaction_highlight_options_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5059|5057");
			context.verify_sub_redaction_highlight_options_not_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_doclist_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{doclist} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", "doc list");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_child_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{child} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", "child");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_right_arrow_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{>} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", ">");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_double_right_arrow_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{>>} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", ">>");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_left_arrow_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{>} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", "<");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_double_left_arrow_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{>>} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", "<<");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_nav_to_other_doc_view_doc_via_history_When_doc_loaded_Then_verify_redactions_menu_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and nav_to_other_doc_view_doc_via_{history} When doc_loaded Then verify_redactions_menu_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("doc_nav", "history");
			context.nav_to_other_doc_view_doc_via_(true, dataMap);
			context.doc_loaded(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4975");
			context.verify_redactions_menu_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_open_highlight_tool_When_click_highlight_tool_Then_verify_highlight_reverts_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and open_highlight_tool When click_highlight_tool Then verify_highlight_reverts_off");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.open_highlight_tool(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4831");
			context.verify_highlight_reverts_off(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_open_redaction_tool_When_click_grey_redact_tool_Then_verify_redaction_reverts_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and open_redaction_tool When click_grey_redact_tool Then verify_redaction_reverts_off");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.open_redaction_tool(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4831");
			context.verify_redaction_reverts_off(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_grey_redact_tool_Then_verify_text_redaction_sub_redactions_option_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_grey_redact_tool Then verify_text_redaction_sub_redactions_option_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6405");
			context.verify_text_redaction_sub_redactions_option_not_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_grey_redact_tool_Then_verify_redaction_submenu_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_grey_redact_tool Then verify_redaction_submenu_off");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4687|4829");
			context.verify_redaction_submenu_off(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_grey_redact_tool_Then_verify_sub_redaction_highlight_options_not_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_grey_redact_tool Then verify_sub_redaction_highlight_options_not_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5059|5057");
			context.verify_sub_redaction_highlight_options_not_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_click_grey_redact_tool_and_click_rectangle_redaction_button_When_click_rectangle_redaction_button_Then_verify_redaction_submenu_off() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and click_grey_redact_tool and click_rectangle_redaction_button When click_rectangle_redaction_button Then verify_redaction_submenu_off");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4687|4829");
			context.verify_redaction_submenu_off(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_rectangle_redactions_When_click_doc_view_print_button_Then_verify_pdf_document_with_redactions_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{rectangle}_redactions When click_doc_view_print_button Then verify_pdf_document_with_redactions_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "rectangle");
			context.on_doc_with_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3689");
			context.verify_pdf_document_with_redactions_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_multipage_redactions_When_click_doc_view_print_button_Then_verify_pdf_document_with_redactions_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{multipage}_redactions When click_doc_view_print_button Then verify_pdf_document_with_redactions_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "multipage");
			context.on_doc_with_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3689");
			context.verify_pdf_document_with_redactions_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_current_redactions_When_click_doc_view_print_button_Then_verify_pdf_document_with_redactions_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{current}_redactions When click_doc_view_print_button Then verify_pdf_document_with_redactions_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "current");
			context.on_doc_with_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3689");
			context.verify_pdf_document_with_redactions_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_all_redactions_When_click_doc_view_print_button_Then_verify_pdf_document_with_redactions_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{all}_redactions When click_doc_view_print_button Then verify_pdf_document_with_redactions_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "all");
			context.on_doc_with_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3689");
			context.verify_pdf_document_with_redactions_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_rectangle_redactions_When_doc_view_download_native_Then_verify_document_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{rectangle}_redactions When doc_view_download_native Then verify_document_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "rectangle");
			context.on_doc_with_redactions(true, dataMap);
			context.doc_view_download_native(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3701");
			context.verify_document_downloaded(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_doc_view_print_button_Then_verify_docid_in_pdf_file_name() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_doc_view_print_button Then verify_docid_in_pdf_file_name");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3647");
			context.verify_docid_in_pdf_file_name(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_rectangle_redactions_When_doc_view_download_txt_Then_verify_document_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{rectangle}_redactions When doc_view_download_txt Then verify_document_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "rectangle");
			context.on_doc_with_redactions(true, dataMap);
			context.doc_view_download_txt(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3701");
			context.verify_document_downloaded(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_rectangle_redactions_When_doc_view_download_pdf_Then_verify_document_downloaded() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_{rectangle}_redactions When doc_view_download_pdf Then verify_document_downloaded");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("redaction_type", "rectangle");
			context.on_doc_with_redactions(true, dataMap);
			context.doc_view_download_pdf(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3701");
			context.verify_document_downloaded(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_When_click_doc_view_print_button_Then_verify_pdf_document_without_redactions_displayed() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions When click_doc_view_print_button Then verify_pdf_document_without_redactions_displayed");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_no_redactions(true, dataMap);
			context.click_doc_view_print_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3688");
			context.verify_pdf_document_without_redactions_displayed(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_pau_and_on_saved_search_page_When_open_saved_search_doc_view_Then_verify_redaction_icon_not_displayed_to_project_admin() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_pau and on_saved_search_page When open_saved_search_doc_view Then verify_redaction_icon_not_displayed_to_project_admin");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("security_group", "Default Security Group");
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_search_doc_view(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "2496");
			context.verify_redaction_icon_not_displayed_to_project_admin(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_this_page_redaction_Then_verify_redaction_applied() throws Throwable
	{
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);


		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_this_page_redaction Then verify_redaction_applied");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("redactionTag", "Default Automation Redaction");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_redactions(true, dataMap);
			context.apply_this_page_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6407");
			context.verify_redaction_applied(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_When_click_applied_redaction_Then_verify_1_redaction_tag_per_redaction() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);
		
		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction When click_applied_redaction Then verify_1_redaction_tag_per_redaction");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.click_applied_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4684");
			context.verify_1_redaction_tag_per_redaction(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_apply_rectangle_highlight_and_reload_the_page_and_rectangle_highlight_deleted_When_reload_the_page_Then_verify_redaction_highlight_deleted_in_doc_view() throws Throwable
	{
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);


		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and apply_rectangle_redaction and rectangle_redaction_deleted and apply_rectangle_highlight and reload_the_page and rectangle_highlight_deleted When reload_the_page Then verify_redaction_highlight_deleted_in_doc_view");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("redactionTag", "Redacted Privilege");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_no_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.apply_rectangle_highlight(true, dataMap);
			context.reload_the_page(true, dataMap);
			context.rectangle_highlight_deleted(true, dataMap);
			context.reload_the_page(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "7848");
			context.verify_redaction_highlight_deleted_in_doc_view(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_rectangle_redaction_Then_verify_redaction_applied() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_rectangle_redaction Then verify_redaction_applied");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6407");
			context.verify_redaction_applied(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_no_redactions_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_When_reload_the_page_Then_verify_redaction_deleted_in_doc_view() throws Throwable
	{

		HashMap dataMap = new HashMap();
		
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);
		

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_no_redactions and apply_rectangle_redaction and rectangle_redaction_deleted When reload_the_page Then verify_redaction_deleted_in_doc_view");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("redactionTag", "Redacted Privilege");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_no_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.reload_the_page(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "7846");
			context.verify_redaction_deleted_in_doc_view(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "Regression"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_SGSame2_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_When_apply_rectangle_redaction_without_changing_tag_Then_verify_redaction_saved_with_last_applied_tag() throws Throwable
	{
		//HashMap dataMap = new HashMap();
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);


		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction_{SGSame2} and log_out and login_to_saved_search_rmu and open_saved_doc_view When apply_rectangle_redaction_without_changing_tag Then verify_redaction_saved_with_last_applied_tag");

		dataMap.put("ExtentTest",test);

		try {
			//Return to these values once EG project reopens
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			//dataMap.put("tag", "SGSame2");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.apply_rectangle_redaction_without_changing_tag(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11385|11386");
			context.verify_redaction_saved_with_last_applied_tag(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "Regression"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_this_page_redaction_SGSame2_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_When_apply_this_page_redaction_without_changing_tag_Then_verify_redaction_saved_with_last_applied_tag() throws Throwable
	{
		//HashMap dataMap = new HashMap();
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);


		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_this_page_redaction_{SGSame2} and log_out and login_to_saved_search_rmu and open_saved_doc_view When apply_this_page_redaction_without_changing_tag Then verify_redaction_saved_with_last_applied_tag");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "SGSame2");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			//context.on_doc_with_redactions(true, dataMap);
			context.apply_this_page_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.apply_this_page_redaction_without_changing_tag(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11385|11386");
			context.verify_redaction_saved_with_last_applied_tag(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smoke99"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame2_When_apply_rectangle_redaction_with_default_tag_Then_verify_edited_redaction_tag_is_latest_redaction_tag() throws Throwable
	{
		//HashMap dataMap = new HashMap();
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and edit_redaction_{true}{SGSame2} When apply_rectangle_redaction_with_default_tag Then verify_edited_redaction_tag_is_latest_redaction_tag");

		dataMap.put("ExtentTest",test);

		try {
			//Change in Json later if want to use old environment
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("docid", "ID00000009");
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.edit_redaction_(true, dataMap);
			context.apply_rectangle_redaction_with_default_tag(true, dataMap);
//			dataMap.put("A", "");
//			dataMap.put("TestCase", "11428");
			context.verify_edited_redaction_tag_is_latest_redaction_tag(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_rectangle_redaction_deleted_and_switch_to_tab_2_When_click_rectangle_redaction_Then_verify_user_cannot_delete_redaction_on_2nd_tab_before_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and rectangle_redaction_deleted and switch_to_tab_2 When click_rectangle_redaction Then verify_user_cannot_delete_redaction_on_2nd_tab_before_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6447");
			context.verify_user_cannot_delete_redaction_on_2nd_tab_before_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_apply_rectangle_redaction_and_switch_to_tab_2_and_click_grey_redact_tool_and_reload_the_page_and_on_doc_with_redactions_When_apply_rectangle_redaction_Then_verify_user_can_apply_redaction_on_2nd_tab_after_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and open_saved_search_doc_view_new_tab and switch_to_tab_1 and apply_rectangle_redaction and switch_to_tab_2 and click_grey_redact_tool and reload_the_page and on_doc_with_redactions When apply_rectangle_redaction Then verify_user_can_apply_redaction_on_2nd_tab_after_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.reload_the_page(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6445");
			context.verify_user_can_apply_redaction_on_2nd_tab_after_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_rectangle_redaction_deleted_and_switch_to_tab_2_When_click_grey_redact_tool_Then_verify_another_user_applied_redaction_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and rectangle_redaction_deleted and switch_to_tab_2 When click_grey_redact_tool Then verify_another_user_applied_redaction_message");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6445|6446|6447");
			context.verify_another_user_applied_redaction_message(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_apply_rectangle_redaction_and_switch_to_tab_2_and_click_grey_redact_tool_When_click_rectangle_redaction_button_Then_verify_user_cannot_add_redaction_on_2nd_tab_before_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and open_saved_search_doc_view_new_tab and switch_to_tab_1 and apply_rectangle_redaction and switch_to_tab_2 and click_grey_redact_tool When click_rectangle_redaction_button Then verify_user_cannot_add_redaction_on_2nd_tab_before_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.click_rectangle_redaction_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6445");
			context.verify_user_cannot_add_redaction_on_2nd_tab_before_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_edit_redaction_trueSGSame2_and_switch_to_tab_2_and_reload_the_page_and_on_doc_with_redactions_When_edit_redaction_trueSGSame2_Then_verify_user_can_edit_redaction_on_2nd_tab_after_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and edit_redaction_{true}{SGSame2} and switch_to_tab_2 and reload_the_page and on_doc_with_redactions When edit_redaction_{true}{SGSame2} Then verify_user_can_edit_redaction_on_2nd_tab_after_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.reload_the_page(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6446");
			context.verify_user_can_edit_redaction_on_2nd_tab_after_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_rectangle_redaction_deleted_and_switch_to_tab_2_and_reload_the_page_When_on_doc_with_redactions_Then_verify_redaction_on_2nd_tab_deleted_after_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and rectangle_redaction_deleted and switch_to_tab_2 and reload_the_page When on_doc_with_redactions Then verify_redaction_on_2nd_tab_deleted_after_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.reload_the_page(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6447");
			context.verify_redaction_on_2nd_tab_deleted_after_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_edit_redaction_trueSGSame1_and_switch_to_tab_2_When_click_rectangle_redaction_Then_verify_user_cannot_edit_redaction_on_2nd_tab_before_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and edit_redaction_{true}{SGSame1} and switch_to_tab_2 When click_rectangle_redaction Then verify_user_cannot_edit_redaction_on_2nd_tab_before_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			dataMap.put("tag", "SGSame1");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6446");
			context.verify_user_cannot_edit_redaction_on_2nd_tab_before_reload(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_apply_rectangle_redaction_and_switch_to_tab_2_When_click_grey_redact_tool_Then_verify_another_user_applied_redaction_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and open_saved_search_doc_view_new_tab and switch_to_tab_1 and apply_rectangle_redaction and switch_to_tab_2 When click_grey_redact_tool Then verify_another_user_applied_redaction_message");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6445|6446|6447");
			context.verify_another_user_applied_redaction_message(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_open_saved_search_doc_view_new_tab_and_switch_to_tab_1_and_edit_redaction_trueSGSame1_and_switch_to_tab_2_When_click_grey_redact_tool_Then_verify_another_user_applied_redaction_message() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and open_saved_search_doc_view_new_tab and switch_to_tab_1 and edit_redaction_{true}{SGSame1} and switch_to_tab_2 When click_grey_redact_tool Then verify_another_user_applied_redaction_message");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.open_saved_search_doc_view_new_tab(true, dataMap);
			context.switch_to_tab_1(true, dataMap);
			dataMap.put("tag", "SGSame1");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_tab_2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6445|6446|6447");
			context.verify_another_user_applied_redaction_message(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}




	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_SGSame2_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_SGSame2_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_place_rectangle_redaction_without_saving_Then_verify_default_redaction_tag_selected_for_2nd_user() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction_{SGSame2} and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction_{SGSame2} and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When place_rectangle_redaction_without_saving Then verify_default_redaction_tag_selected_for_2nd_user");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("tag", "SGSame2");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("tag", "SGSame2");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11436");
			context.verify_default_redaction_tag_selected_for_2nd_user(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smoke79"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_add_temp_redaction_tag_and_on_saved_search_page_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_TempRedaction_and_apply_rectangle_redaction_TempRedaction_and_delete_temp_redaction_tag_and_on_saved_search_page_and_open_saved_doc_view_When_place_rectangle_redaction_without_saving_Then_verify_last_saved_tag_used_for_new_redaction_after_redaction_tag_deletion() throws Throwable
	{
		//HashMap dataMap = new HashMap();
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and add_temp_redaction_tag and on_saved_search_page and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction_{TempRedaction} and apply_rectangle_redaction_{TempRedaction} and delete_temp_redaction_tag and on_saved_search_page and open_saved_doc_view When place_rectangle_redaction_without_saving Then verify_last_saved_tag_used_for_new_redaction_after_redaction_tag_deletion");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "TempRedaction");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.add_temp_redaction_tag(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);

			dataMap.put("tag", "Default Redaction Tag");
			dataMap.put("firstTag", "Default Redaction Tag");
			context.apply_rectangle_redaction_(true, dataMap);
			dataMap.put("tag", "TempRedaction");
			context.apply_rectangle_redaction_(true, dataMap);
			context.delete_temp_redaction_tag(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11434");
			context.verify_last_saved_tag_used_for_new_redaction_after_redaction_tag_deletion(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smoke79"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_add_temp_redaction_tag_and_on_saved_search_page_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_TempRedaction_and_apply_rectangle_redaction_TempRedaction_and_delete_temp_redaction_tag_and_on_saved_search_page_and_open_saved_doc_view_When_click_previously_placed_redaction_Then_verify_default_tag_selected_for_existing_redaction_after_redaction_tag_deletion() throws Throwable
	{
		//HashMap dataMap = new HashMap();
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and add_temp_redaction_tag and on_saved_search_page and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction_{TempRedaction} and apply_rectangle_redaction_{TempRedaction} and delete_temp_redaction_tag and on_saved_search_page and open_saved_doc_view When click_previously_placed_redaction Then verify_default_tag_selected_for_existing_redaction_after_redaction_tag_deletion");

		dataMap.put("ExtentTest",test);

		try {
			/*
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			*/
			context.login_to_saved_search_rmu(true, dataMap);
			context.add_temp_redaction_tag(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("tag", "Default Redaction Tag");
			context.apply_rectangle_redaction_(true, dataMap);
			dataMap.put("tag", "TempRedaction");
			context.apply_rectangle_redaction_(true, dataMap);
			context.delete_temp_redaction_tag(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.click_previously_placed_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11435");
			context.verify_default_tag_selected_for_existing_redaction_after_redaction_tag_deletion(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_edit_redaction_truetag_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_When_on_doc_with_redactions_Then_verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and edit_redaction_{true}{tag} and log_out and login_to_saved_search_rmu and open_saved_doc_view When on_doc_with_redactions Then verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			dataMap.put("search_group", "My Saved Search");
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			dataMap.put("search_group", "My Saved Search");
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			dataMap.put("search_group", "My Saved Search");
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5753");
			context.verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smoke45"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_When_on_doc_with_redactions_Then_verify_redaction_displayed_for_diff_sg_with_same_annotation_layer() throws Throwable
	{
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and log_out and login_to_saved_search_rmu and open_saved_doc_view When on_doc_with_redactions Then verify_redaction_displayed_for_diff_sg_with_same_annotation_layer");

		dataMap.put("ExtentTest",test);

		try {
			
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.log_out(true, dataMap);
			
			dataMap.put("security_group", "SG3");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("TestCase", "5746|3203");
			context.verify_redaction_displayed_for_diff_sg_with_same_annotation_layer(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smoke45"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_edit_redaction_truetag_Then_verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer() throws Throwable
	{
		String methodName = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		getMethodData(dataMap,methodName);


		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When edit_redaction_{true}{tag} Then verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer");

		dataMap.put("ExtentTest",test);

		try {
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("security_group", "SG3");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5753");
			context.verify_edited_redaction_displayed_for_diff_sg_with_same_annotation_layer(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame2_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_When_edit_redaction_trueSGSame2_Then_verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and edit_redaction_{true}{SGSame2} and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction When edit_redaction_{true}{SGSame2} Then verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6594");
			context.verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_When_rectangle_redaction_deleted_Then_verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and rectangle_redaction_deleted and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction When rectangle_redaction_deleted Then verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6594");
			context.verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_rectangle_redaction_Then_verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and log_out and login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_rectangle_redaction Then verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG5");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6594");
			context.verify_redactions_with_diff_sg_diff_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_When_click_highlight_tool_Then_verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 When click_highlight_tool Then verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("TestCase", "6591");
			context.verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive" , "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 When click_grey_redact_tool Then verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame2_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame2} and switch_to_user2 When click_grey_redact_tool Then verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 When click_grey_redact_tool Then verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "SGSame2");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame2_and_switch_to_user2_When_click_highlight_tool_Then_verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame2} and switch_to_user2 When click_highlight_tool Then verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("TestCase", "6591");
			context.verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_click_highlight_tool_Then_verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 When click_highlight_tool Then verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("TestCase", "6591");
			context.verify_highlight_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_When_on_doc_with_redactions_Then_verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 When on_doc_with_redactions Then verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("delete", true);
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame2_and_switch_to_user2_When_on_doc_with_redactions_Then_verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame2} and switch_to_user2 When on_doc_with_redactions Then verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			dataMap.put("dimensions", "true");

			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_on_doc_with_redactions_Then_verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 When on_doc_with_redactions Then verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_redaction_displayed_after_reload_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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

	@Test(groups = {"DocView, Positive", "smokey"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_on_doc_with_redactions_verify_remarks_error_with_diff_sg_same_annotation_layer_same_redaction_tags() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("verify_remarks_error_with_diff_sg_same_annotation_layer_same_redaction_tags");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("uid", "qapau5@consilio.com");
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("tag", "SGSame2");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.click_remark_button(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6591");
			context.verify_remarks_error_with_diff_sg_same_annotation_layer_same_redaction_tags(true, dataMap);
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



	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_and_apply_rectangle_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_When_place_rectangle_redaction_without_saving_Then_verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} and apply_rectangle_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} When place_rectangle_redaction_without_saving Then verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11440");
			context.verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_and_apply_this_page_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_When_place_this_page_redaction_without_saving_Then_verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} and apply_this_page_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} When place_this_page_redaction_without_saving Then verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_this_page_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			context.place_this_page_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11440");
			context.verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_and_apply_this_page_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_When_place_this_page_redaction_without_saving_Then_verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} and apply_this_page_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} When place_this_page_redaction_without_saving Then verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_this_page_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			context.place_this_page_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11440");
			context.verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_and_apply_rectangle_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_When_place_rectangle_redaction_without_saving_Then_verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} and apply_rectangle_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} When place_rectangle_redaction_without_saving Then verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG7");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11440");
			context.verify_auto_selected_tag_with_diff_sg_same_annotation_layer_diff_redaction_tags_released_docs(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive", "smokey23"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_and_apply_rectangle_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_When_place_rectangle_redaction_without_saving_Then_verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} and apply_rectangle_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} When place_rectangle_redaction_without_saving Then verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11439");
			context.verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey23"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_and_apply_this_page_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_When_place_this_page_redaction_without_saving_Then_verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} and apply_this_page_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} When place_this_page_redaction_without_saving Then verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_this_page_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			context.place_this_page_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11439");
			context.verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey23"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_and_apply_this_page_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000006_When_place_this_page_redaction_without_saving_Then_verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} and apply_this_page_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000006} When place_this_page_redaction_without_saving Then verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_this_page_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000006");
			context.select_docview_doc_(true, dataMap);
			context.place_this_page_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11439");
			context.verify_auto_selected_tag_on_propagated_doc_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey23"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_and_apply_rectangle_redaction_SGSame1_and_log_out_and_login_to_saved_search_rmu_and_open_saved_doc_view_and_select_docview_doc_ID00000004_When_place_rectangle_redaction_without_saving_Then_verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} and apply_rectangle_redaction_{SGSame1} and log_out and login_to_saved_search_rmu and open_saved_doc_view and select_docview_doc_{ID00000004} When place_rectangle_redaction_without_saving Then verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			dataMap.put("tag", "SGSame1");
			context.apply_rectangle_redaction_(true, dataMap);
			context.log_out(true, dataMap);
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG6");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			dataMap.put("docid", "ID00000004");
			context.select_docview_doc_(true, dataMap);
			context.place_rectangle_redaction_without_saving(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "11439");
			context.verify_auto_selected_tag_with_diff_sg_same_annotation_layer_same_redaction_tags_released_docs(true, dataMap);
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


	@Test(groups = {"DocView, Positive"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_and_on_saved_search_page_and_open_saved_doc_view_When_on_audio_doc_with_redactions_Then_verify_audio_redaction_not_displayed_from_different_security_group() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login_as_rmu and on_saved_search_page and open_saved_doc_view When on_audio_doc_with_redactions Then verify_audio_redaction_not_displayed_from_different_security_group");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG2");
			dataMap.put("domain", "Not a Domain");
			context.login_as_rmu(true, dataMap);
			context.on_saved_search_page(true, dataMap);
			dataMap.put("search_group", "Shared with SG2");
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_with_redactions(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "3484");
			context.verify_audio_redaction_not_displayed_from_different_security_group(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"DocView, Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 When click_grey_redact_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6687");
			context.verify_message_to_reload_same_document(true, dataMap);
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



	@Test(groups = {"DocView, Positive","smokey7"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_rectangle_highlight_Then_verify_doc_view_analytics_panel_unchanged() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_rectangle_highlight Then verify_doc_view_analytics_panel_unchanged");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Default Automation Redaction");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.get_analytics_data_prestate(true, dataMap);
			context.apply_rectangle_highlight(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5377");
			context.verify_doc_view_analytics_panel_unchanged(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey7"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_reviewer_remark_Then_verify_doc_view_analytics_panel_unchanged() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_reviewer_remark Then verify_doc_view_analytics_panel_unchanged");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Default Automation Redaction");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.get_analytics_data_prestate(true, dataMap);
			context.apply_reviewer_remark(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5377");
			context.verify_doc_view_analytics_panel_unchanged(true, dataMap);
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


	@Test(groups = {"DocView, Positive", "smokey7"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_When_apply_rectangle_redaction_Then_verify_doc_view_analytics_panel_unchanged() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions When apply_rectangle_redaction Then verify_doc_view_analytics_panel_unchanged");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Default Automation Redaction");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.get_analytics_data_prestate(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "5377");
			context.verify_doc_view_analytics_panel_unchanged(true, dataMap);
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

	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_When_apply_audio_redaction_emptyemptyRedactionTag1_Then_verify_no_audio_redaction_time_selected_error() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions When apply_audio_redaction_{empty}{empty}{RedactionTag1} Then verify_no_audio_redaction_time_selected_error");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "empty");
			dataMap.put("starttime", "empty");
			dataMap.put("A", "");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "4816");
			context.verify_no_audio_redaction_time_selected_error(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_When_apply_audio_redaction_000200000100RedactionTag1_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions When apply_audio_redaction_{000200}{000100}{RedactionTag1} Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000200");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_000000000100RedactionTag1_When_edit_audio_redaction_000200000100RedactionTag1_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{000000}{000100}{RedactionTag1} When edit_audio_redaction_{000200}{000100}{RedactionTag1} Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000200");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.edit_audio_redaction_(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_030000000100RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{030000}{000100}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "030000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_000000030000RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{000000}{030000}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "030000");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_030000033000RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{030000}{033000}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "033000");
			dataMap.put("starttime", "030000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_000000000100RedactionTag1_and_edit_audio_redaction_030000000100RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{000000}{000100}{RedactionTag1} and edit_audio_redaction_{030000}{000100}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "030000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.edit_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_000000000100RedactionTag1_and_edit_audio_redaction_000000030000RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{000000}{000100}{RedactionTag1} and edit_audio_redaction_{000000}{030000}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("endtime", "030000");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.edit_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_and_apply_audio_redaction_000000000100RedactionTag1_and_edit_audio_redaction_030000033000RedactionTag1_When_audio_redaction_attempted_save_Then_verify_audio_redaction_not_saved() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions and apply_audio_redaction_{000000}{000100}{RedactionTag1} and edit_audio_redaction_{030000}{033000}{RedactionTag1} When audio_redaction_attempted_save Then verify_audio_redaction_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			dataMap.put("endtime", "000100");
			dataMap.put("starttime", "000000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.apply_audio_redaction_(true, dataMap);
			dataMap.put("endtime", "033000");
			dataMap.put("starttime", "030000");
			dataMap.put("redactiontag", "Redaction Tag 1");
			context.edit_audio_redaction_(true, dataMap);
			context.audio_redaction_attempted_save(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6465|6466");
			context.verify_audio_redaction_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView, Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_audio_doc_without_redactions_When_apply_audio_redaction_Then_verify_audio_redaction_time_format() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_audio_doc_without_redactions When apply_audio_redaction Then verify_audio_redaction_time_format");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "Audio100");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_audio_doc_without_redactions(true, dataMap);
			context.apply_audio_redaction(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6464");
			context.verify_audio_redaction_time_format(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_When_click_highlight_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 When click_highlight_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_message_to_reload_same_document(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_When_click_highlight_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 When click_highlight_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_message_to_reload_same_document(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame1_and_switch_to_user2_When_click_highlight_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame1} and switch_to_user2 When click_highlight_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_highlight_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_message_to_reload_same_document(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 When click_grey_redact_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_message_to_reload_same_document(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame1_and_switch_to_user2_When_click_grey_redact_tool_Then_verify_message_to_reload_same_document() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame1} and switch_to_user2 When click_grey_redact_tool Then verify_message_to_reload_same_document");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("tag", "SGSame1");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_message_to_reload_same_document(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_and_switch_to_user2_and_click_grey_redact_tool_When_on_doc_with_redactions_Then_verify_user1_redaction_change_displayed_after_user2_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and rectangle_redaction_deleted and switch_to_user2 and click_grey_redact_tool When on_doc_with_redactions Then verify_user1_redaction_change_displayed_after_user2_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			dataMap.put("delete", true);
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.verify_user1_redaction_change_displayed_after_user2_reload(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_switch_to_user2_and_click_grey_redact_tool_When_on_doc_with_redactions_Then_verify_user1_redaction_change_displayed_after_user2_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and switch_to_user2 and click_grey_redact_tool When on_doc_with_redactions Then verify_user1_redaction_change_displayed_after_user2_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.verify_user1_redaction_change_displayed_after_user2_reload(true, dataMap);
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


	@Test(groups = {"DocView", "Positive", "smokey44"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_login_to_saved_search_rmu_private_browser_and_open_saved_doc_view_and_on_doc_with_redactions_and_switch_to_user1_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame1_and_switch_to_user2_and_click_grey_redact_tool_When_on_doc_with_redactions_Then_verify_user1_redaction_change_displayed_after_user2_reload() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and login_to_saved_search_rmu_private_browser and open_saved_doc_view and on_doc_with_redactions and switch_to_user1 and apply_rectangle_redaction and edit_redaction_{true}{SGSame1} and switch_to_user2 and click_grey_redact_tool When on_doc_with_redactions Then verify_user1_redaction_change_displayed_after_user2_reload");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "08122020_NV");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "Default Security Group");
			dataMap.put("domain", "Not a Domain");
			dataMap.put("tag", "Redacted Privacy");
			dataMap.put("dimensions", "true");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			dataMap.put("uid", "sqa.consilio3@sqapowered.com");
			dataMap.put("pwd", "Q@test_10");
			context.login_to_saved_search_rmu_private_browser(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.switch_to_user1(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.switch_to_user2(true, dataMap);
			context.click_grey_redact_tool(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.verify_user1_redaction_change_displayed_after_user2_reload(true, dataMap);
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


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_rectangle_redaction_deleted_When_query_annotation_layer_in_db_Then_verify_redaction_username_in_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and rectangle_redaction_deleted When query_annotation_layer_in_db Then verify_redaction_username_in_db");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.rectangle_redaction_deleted(true, dataMap);
			context.query_annotation_layer_in_db(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_redaction_username_in_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_and_edit_redaction_trueSGSame1_When_query_annotation_layer_in_db_Then_verify_redaction_username_in_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction and edit_redaction_{true}{SGSame1} When query_annotation_layer_in_db Then verify_redaction_username_in_db");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			dataMap.put("tag", "SGSame1");
			dataMap.put("dimensions", "true");
			context.edit_redaction_(true, dataMap);
			context.query_annotation_layer_in_db(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_redaction_username_in_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"DocView", "Positive"})
	public void test_Given_login_to_saved_search_rmu_and_open_saved_doc_view_and_on_doc_with_redactions_and_apply_rectangle_redaction_When_query_annotation_layer_in_db_Then_verify_redaction_username_in_db() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given login_to_saved_search_rmu and open_saved_doc_view and on_doc_with_redactions and apply_rectangle_redaction When query_annotation_layer_in_db Then verify_redaction_username_in_db");

		dataMap.put("ExtentTest",test);

		try {
			dataMap.put("project", "021320_EG");
			dataMap.put("impersonate", "Review Manager");
			dataMap.put("security_group", "SG1");
			dataMap.put("domain", "Not a Domain");
			context.login_to_saved_search_rmu(true, dataMap);
			context.open_saved_doc_view(true, dataMap);
			context.on_doc_with_redactions(true, dataMap);
			context.apply_rectangle_redaction(true, dataMap);
			context.query_annotation_layer_in_db(true, dataMap);
			dataMap.put("A", "");
			dataMap.put("TestCase", "6442|6443|6444");
			context.verify_redaction_username_in_db(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
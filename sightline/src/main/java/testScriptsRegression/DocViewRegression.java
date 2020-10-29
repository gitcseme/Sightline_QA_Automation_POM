package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.DocViewContext;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class DocViewRegression extends RegressionBase {

	DocViewContext context = new DocViewContext();


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

	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive" ,"smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive","smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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

	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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


	@Test(groups = {"DocView, Positive", "smoke14"})
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
}//eof
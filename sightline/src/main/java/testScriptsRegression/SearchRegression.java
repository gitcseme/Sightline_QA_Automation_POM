package testScriptsRegression;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.SearchContext;
import pageFactory.SessionSearch;
import automationLibrary.Element;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked" })
public class SearchRegression extends RegressionBase {

	SearchContext context = new SearchContext();

	@Test(groups = {"Search, Positive"})
	public void test_Given_sightline_is_launched_and_login_When_goto_search_session_page_Then_on_production_Search_Session_page() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and login When goto_search_session_page Then on_production_Search_Session_page");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_verify_searched_save_When_Then_Not_verify_current_login_session_previous_search_query_selection() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_searched_save When  Then [Not] verify_current_login_session_previous_search_query_selection");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
			//context.(true, dataMap);
			context.verify_current_login_session_previous_search_query_selection(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Search, Positive"})
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_previous_search_query_selection() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save When  Then verify_current_login_session_previous_search_query_selection");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
			}
			//context.(true, dataMap);
			context.verify_current_login_session_previous_search_query_selection(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Positive"})
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_saved_search_SEARCH5() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save When  Then verify_current_login_session_saved_search_SEARCH5");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
			}
			//context.(true, dataMap);
			context.verify_current_login_session_saved_search_SEARCH5(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Positive"})
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_edit_previous_search_query() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save When  Then verify_current_login_session_edit_previous_search_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
			}
			//context.(true, dataMap);
			context.verify_current_login_session_edit_previous_search_query(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Positive"})
	public void test_Given_verify_searched_save_and_modify_search_criteria_When_Then_verify_user_modified_session_query_not_changed_saved_query() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save and modify_search_criteria When  Then verify_user_modified_session_query_not_changed_saved_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
			}
			//context.(true, dataMap);
			context.verify_user_modified_session_query_not_changed_saved_query(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchForEach_MetaData_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[ForEach_MetaData] When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			SessionSearch searchSession = (SessionSearch) dataMap.get("searchSession");
			Element metaDataElement = searchSession.getMetaOption();
			for (String option : metaDataOptions) {
				dataMap.put("metaDataOption",option);
				context.create_search(true, dataMap);
				//context.(true, dataMap);
				context.verify_search_criteria(true, dataMap);
			}
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchnTimes_When_save_search_Then_verify_searched_save() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[nTimes] When save_search Then verify_searched_save");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("PWD", "Consilio!SQRdIam1!");
			dataMap.put("UID", "qapau5");
			context.login(true, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
			}
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_verify_searched_save_When_Then_Not_verify_current_login_session_saved_search_SEARCH5() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_searched_save When  Then [Not] verify_current_login_session_saved_search_SEARCH5");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
			//context.(true, dataMap);
			context.verify_current_login_session_saved_search_SEARCH5(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_verify_searched_save_When_Then_Not_verify_current_login_session_edit_previous_search_query() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_searched_save When  Then [Not] verify_current_login_session_edit_previous_search_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
			//context.(true, dataMap);
			context.verify_current_login_session_edit_previous_search_query(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_sightline_is_launched_When_goto_search_session_page_Then_Not_on_production_Search_Session_page() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] sightline_is_launched When goto_search_session_page Then [Not] on_production_Search_Session_page");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_sightline_is_launched_and_Not_login_When_goto_search_session_page_Then_Not_on_production_Search_Session_page() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given sightline_is_launched and [Not] login When goto_search_session_page Then [Not] on_production_Search_Session_page");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_verify_searched_save_When_Then_Not_verify_user_modified_session_query_not_changed_saved_query() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_searched_save When  Then [Not] verify_user_modified_session_query_not_changed_saved_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
			//context.(true, dataMap);
			context.verify_user_modified_session_query_not_changed_saved_query(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_verify_searched_save_and_Not_modify_search_criteria_When_Then_Not_verify_user_modified_session_query_not_changed_saved_query() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save and [Not] modify_search_criteria When  Then [Not] verify_user_modified_session_query_not_changed_saved_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(true, dataMap);
			context.modify_search_criteria(false, dataMap);
			//context.(true, dataMap);
			context.verify_user_modified_session_query_not_changed_saved_query(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			//context.(true, dataMap);
			context.verify_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchForEach_MetaData_When_Then_Not_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[ForEach_MetaData] When  Then [Not] verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			SessionSearch searchSession = (SessionSearch) dataMap.get("searchSession");
			Element metaDataElement = searchSession.getMetaOption();
			dataMap.put("metaDataOption",option);

			context.create_search(false, dataMap);
			//context.(true, dataMap);
			context.verify_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_Not_on_production_Search_Session_page_When_save_search_Then_Not_verify_searched_save() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When save_search Then [Not] verify_searched_save");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchnTimes_When_save_search_Then_Not_verify_searched_save() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[nTimes] When save_search Then [Not] verify_searched_save");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			dataMap.put("prod_template", "1");
			dataMap.put("A", "");
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.save_search(true, dataMap);
			context.verify_searched_save(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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

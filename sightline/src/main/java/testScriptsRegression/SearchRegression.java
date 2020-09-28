package testScriptsRegression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
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
			Thread.sleep(2000);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			//SessionSearch sessionSearch = (SessionSearch) dataMap.get("sessionSearch");
			//Element metaDataElement = sessionSearch.getMetaOption();
			//List<WebElement> metaDataOptions = metaDataElement.selectFromDropdown().getOptions();
			List<String> metaDataOptions= new ArrayList<String>();
			metaDataOptions.add("EmailAuthorDomain");
			metaDataOptions.add("EmailRecipientNames");
			metaDataOptions.add("DocFileType");
			
			for (String option : metaDataOptions) {
				dataMap.put("metaDataOption",option);
				context.create_search(true, dataMap);
				//context.(true, dataMap);
				context.verify_search_criteria(true, dataMap);
				context.remove_search_criteria(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
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
			dataMap.put("metaDataOption","EmailAuthorDomain");

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

	@Test(groups = {"Search, Positive", "smoke"})
	public void test_Given_on_production_Search_Session_page_and_create_searchis_When_Then_verify_is_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[is] When  Then verify_is_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "5");
			dataMap.put("metaDataOption", "EmailAllDomainCount");
			dataMap.put("searchType","IS");
			context.create_search(true, dataMap);
			context.verify_is_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_search_When_Then_verify_fulltext_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[full_text_search] When  Then verify_fulltext_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"P Allen\" AND CustodianName: \"P Vinod\"");
			dataMap.put("searchType","FULLTEXT");
			context.create_search(true, dataMap);
			context.verify_fulltext_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_When_Then_verify_range_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[range] When  Then verify_range_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","RANGE");
			dataMap.put("metaDataOption","");
			dataMap.put("metaDataValue","");
			dataMap.put("metaDataVal2","");
			context.create_search(true, dataMap);
			context.verify_range_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_is_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_is_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			context.verify_is_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchis_When_Then_Not_verify_is_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[is] When  Then [Not] verify_is_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","IS");
			context.create_search(false, dataMap);
			context.verify_is_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_fulltext_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_fulltext_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			context.verify_fulltext_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchfull_text_search_When_Then_Not_verify_fulltext_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[full_text_search] When  Then [Not] verify_fulltext_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","FULLTEXT");
			context.create_search(false, dataMap);
			context.verify_fulltext_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_long_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_long_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			dataMap.put("searchType","LONG");
			context.create_search(false, dataMap);
			context.verify_long_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchlong_search_When_Then_Not_verify_long_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[long_search] When  Then [Not] verify_long_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","LONG");
			context.create_search(false, dataMap);
			context.verify_long_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_range_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_range_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(false, dataMap);
			context.verify_range_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchrange_When_Then_Not_verify_range_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search[range] When  Then [Not] verify_range_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","RANGE");
			context.create_search(false, dataMap);
			context.verify_range_search_criteria(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_verify_long_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_long_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "P Allen");
			dataMap.put("condition", "OR");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P Vinod");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType","LONG");
			context.create_search(false, dataMap);
			context.verify_long_search_criteria(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_verify_range_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_range_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","RANGE");
			context.create_search(false, dataMap);
			context.verify_range_search_criteria(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_search_When_Then_verify_long_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[long_search] When  Then verify_long_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "P Allen");
			dataMap.put("F", "OR");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P Vinod");
			dataMap.put("metaDataOption1", "CustodianName");
			dataMap.put("searchType","LONG");
			context.create_search(false, dataMap);
			context.verify_long_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_verify_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailAuthorDomain");
			context.create_search(false, dataMap);
			context.verify_search_criteria(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_verify_is_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_is_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "5");
			dataMap.put("metaDataOption", "EmailDomainClount");
			dataMap.put("searchType","is");
			context.create_search(false, dataMap);
			context.verify_is_search_criteria(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_verify_fulltext_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_fulltext_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Consilio!SQRdIam1!");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"P Allen\" AND CustodianName: \"P Vinod\"");
			dataMap.put("searchType","FULLTEXT");
			context.create_search(false, dataMap);
			context.verify_fulltext_search_criteria(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_verify_long_search_criteria_When_click_search_Then_Not_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_long_search_criteria When click_search Then [Not] verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","LONG");
			context.create_search(false, dataMap);
			context.verify_long_search_criteria(false, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_verify_range_search_criteria_When_click_search_Then_Not_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_range_search_criteria When click_search Then [Not] verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","RANGE");
			context.create_search(false, dataMap);
			context.verify_range_search_criteria(false, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_verify_search_criteria_When_click_search_Then_Not_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_search_criteria When click_search Then [Not] verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
			context.verify_search_criteria(false, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_verify_is_search_criteria_When_click_search_Then_Not_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_is_search_criteria When click_search Then [Not] verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","IS");
			context.create_search(false, dataMap);
			context.verify_is_search_criteria(false, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_verify_fulltext_search_criteria_When_click_search_Then_Not_verify_search_returned() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] verify_fulltext_search_criteria When click_search Then [Not] verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","FULLTEXT");
			context.create_search(false, dataMap);
			context.verify_fulltext_search_criteria(false, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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

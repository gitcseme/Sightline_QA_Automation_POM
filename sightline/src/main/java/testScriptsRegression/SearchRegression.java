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
			dataMap.put("nTimes", "1");
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

	@Test(groups = {"Search, Positive"})
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
			dataMap.put("pwd", "Q@test_10");
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","range");
			dataMap.put("metaDataOption","AttachCount");
			dataMap.put("metaDataValue","100");
			dataMap.put("metaDataVal2","1000");
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
			dataMap.put("pwd", "Q@test_10");
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
			context.create_search(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchType","RANGE");
			dataMap.put("metaDataOption","AttachCount");
			dataMap.put("metaDataValue","100");
			dataMap.put("metaDataVal2","1000");
			context.create_search(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "P Allen");
			dataMap.put("condition", "OR");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P Vinod");
			dataMap.put("metaDataOption1", "CustodianName");
			dataMap.put("searchType","LONG");
			context.create_search(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailAuthorDomain");
			context.create_search(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "5");
			dataMap.put("metaDataOption", "EmailAllDomainCount");
			dataMap.put("searchType","is");
			context.create_search(true, dataMap);
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
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau5@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"P Allen\" AND CustodianName: \"P Vinod\"");
			dataMap.put("searchType","FULLTEXT");
			context.create_search(true, dataMap);
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

	//Start Here
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_select_advanced_search_with_CustodianName_When_Then_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and select_advanced_search{_with_CustodianName} When  Then verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "test");
			dataMap.put("MetaDataValue", "ing");
			context.select_advanced_search(true, dataMap);
			context.verify_autosuggest(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive", "smoke"})
	public void test_Given_on_production_Search_Session_page_and_select_search_with_metaDataValue_causes_autosuggest_When_Then_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#5708");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and select_search{_with_metaDataValue_causes_autosuggest|TC#5708} When  Then verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", ".doc");
			dataMap.put("AdditionalKeys", "x");
			dataMap.put("metaDataOption", "DocFileExtension");
			context.select_search(true, dataMap);
			context.verify_autosuggest(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_Not_on_production_Search_Session_page_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given [Not] on_production_Search_Session_page When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(false, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	
	//Complete + Pass
	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_select_advanced_search_with_custodianName_not_match_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11228");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_advanced_search{_with_custodianName_not_match|TC#11228} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_pau(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "Andr");
			dataMap.put("MetaDataValue2", "pppp");
			context.select_advanced_search(true, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//Complete + Pass
	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_select_advanced_search_with_CustodianName_not_match_UpperCase_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11284");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_advanced_search{_with_CustodianName_not_match_UpperCase|TC#11284} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "AndR");
			dataMap.put("MetaDataValue2", "Z");
			context.select_advanced_search(true, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_select_advanced_search_with_CustodianName_not_match_Special_Character_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11286");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_advanced_search{_with_CustodianName_not_match_Special_Character|TC#11286} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "Andr");
			dataMap.put("MetaDataValue2", "&%s");
			context.select_advanced_search(true, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Negative"})
	public void test_Given_on_production_Search_Session_page_and_Not_select_advanced_search_with_CustodianName_not_match_White_Space_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11287");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_advanced_search{_with_CustodianName_not_match_White_Space|TC#11287} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "Andr");
			dataMap.put("MetaDataValue2", "  Loyd");
			context.select_advanced_search(true, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_select_searchwith_matching_letters_for_autosuggest_till_not_match_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11233");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_search{with_matching_letters_for_autosuggest_till_not_match|TC#11233} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("AdditionalKeys", "de");
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "abc");
			context.select_search(false, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_select_search_with_matching_digits_for_autosuggest_till_not_match_When_Then_Not_verify_autosuggest() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11280");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] select_search{_with_matching_digits_for_autosuggest_till_not_match|TC#11280} When  Then [Not] verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("AdditionalKeys", "45");
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "123");
			context.select_search(false, dataMap);
			context.verify_autosuggest(false, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	//What is this?
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10268");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_simple_regex|TC#10268} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"(\"##[a-z]{3}\") Truthful\"~6");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10270");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_simple_regex_hyphen|TC#10270} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"##235-[0-9]{3}-[0-9]{4} Number\"~7");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11549");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_or|TC#11549} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "company OR (\"ProximitySearch Iterative\"~15 OR m0ney) OR TruthFinder");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11550");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_and|TC#11550} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "(\"ProximitySearch Iterative\"~15 AND financial) OR m0ney");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11551");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_not|TC#11551} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "( \"ProximitySearch Iterative\"~15 AND financial ) NOT m0ney");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11552");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_regex|TC#11552} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"(SmartSea* Reca*) ((\"##[a-z]{8}\") develope*)\"~20");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11553");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_regex_combo|TC#11553} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"(SmartSearch  OR  develope*) (\"##precis?on.\"  OR  w?ere   OR  m?ne*)\"~10");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#9601");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity|TC#9601} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"Truthful\"~6");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10521");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_wildcard|TC#10521} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"Truth*\"~6");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_warning_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10240");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_wildcard_warning|TC#10240} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("MetaDataOption", "CustodianName");
			dataMap.put("MetaDataValue", "\"Truth*\"~6");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Come back
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10267");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_simple_regex|TC#10267} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"(\"##[a-z]{3}\") Truthful\"~6");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10269");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_simple_regex_hyphen|TC#10269} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"##235-[0-9]{3}-[0-9]{4} Number\"~7");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11554");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_or|TC#11554} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "company OR (\"ProximitySearch Iterative\"~15 OR m0ney) OR TruthFinder");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11555");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_and|TC#11555} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "(\"ProximitySearch Iterative\"~15 AND financial) OR m0ney");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11556");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_not|TC#11556} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "( \"ProximitySearch Iterative\"~15 AND financial ) NOT m0ney");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11557");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_regex|TC#11557} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"(SmartSea* Reca*) ((\"##[a-z]{8}\") develope*)\"~20");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11558");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_regex_combo|TC#11558} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"(SmartSearch  OR  develope*) (\"##precis?on.\"  OR  w?ere   OR  m?ne*)\"~10");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_advanced_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchsearchTypeTitle_When_Then_Not_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search{searchType}{Title} When  Then [Not] verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_advanced_searchsearchTypeTitle_When_Then_Not_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_advanced_search{searchType}{Title} When  Then [Not] verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			context.create_advanced_search(false, dataMap);
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


	//Completed + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_Number_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10298");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_Number|TC#10298} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "##0\\.375");
			/*
			dataMap.put("State", "yes");
			dataMap.put("metaDataOption", "Number");
			context.verify_search_criteria(true, dataMap);
			*/

			//Changed Contexts Based on the following
			//	 There Is no "Number metaDataOption"
			//   on TFS: For this test case it states: Enter query as "##12" OR "##0\.375" 
			//   Therefor This is a "Long" Query, with OR operator. And should have the correct dataMap Parameters 
			//   Verify Long Search context should be used as outcome 

			dataMap.put("searchType", "LONG");
			dataMap.put("condition", "OR");
			dataMap.put("metaDataValue2", "##12");
			context.create_search(true, dataMap);
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


	//Missing MetaDataOption from Consilio? Phone Number
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_Phone_Number_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10299");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_Phone_Number|TC#10299} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "##[0-9]{3}-[0-9][3]-[0-9]{4}");
			dataMap.put("State", "yes");
			dataMap.put("metaDataOption", "Phone Number");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_EmailAuthorAddress_regex_all_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10303");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_EmailAuthorAddress_regex_all|TC#10303} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "##all");
			dataMap.put("metaDataOption", "EmailAuthorAddress");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_EmailSubject_regex_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10305");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_EmailSubject_regex|TC#10305} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "## Test mail [0-9]{4}/[0-9]{2}/[07]");
			dataMap.put("metaDataOption", "EmailSubject");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_EmailSubject_regex_numeric_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10313");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_EmailSubject_regex_numeric|TC#10313} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "##[0-9]{2}[0-9]{2}[0-9]{2}");
			dataMap.put("metaDataOption", "EmailSubject");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Missing MetaDataOption from Consilio? Comments
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_Comments_regex_PF_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10322");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_Comments_regex_PF|TC#10322} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {

			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "##PF[09]{4}");
			dataMap.put("metaDataOption", "Comments");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_symbol_in_search_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10484");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_@_symbol_in_search|TC#10484} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {

			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "this is a @subject");
			dataMap.put("metaDataOption", "EmailSubject");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}


	//Complete + Pass
	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_symbol_in_email_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#10487");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{_with_@_symbol_in_email|TC#10487} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {

			context.startUP(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "gnunez@concilio.com");
			dataMap.put("metaDataOption", "EmailAuthorAddress");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_Not_create_searchTitle_When_Then_Not_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and [Not] create_search{Title} When  Then [Not] verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			context.create_search(false, dataMap);
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

	@Test(groups = {"Search, Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchis_with_EmailDomainCount_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#153");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{is}{_with_EmailDomainCount|TC#153} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "5");
			dataMap.put("State", "yes");
			dataMap.put("metaDataOption", "EmailDomainCount");
			dataMap.put("searchType", "is");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchis_with_EmailSentDate_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8058");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{is}{_with_EmailSentDate|TC#8058} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "[date|time]");
			dataMap.put("State", "yes");
			dataMap.put("metaDataOption", "EmailSentDate");
			dataMap.put("searchType", "is");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchis_with_EmailSentDate_date_only_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8080");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{is}{_with_EmailSentDate_date_only|TC#8080} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "[date]");
			dataMap.put("State", "no");
			dataMap.put("metaDataOption", "EmailSentDate");
			dataMap.put("searchType", "is");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchis_with_CustomField_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8110");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{is}{_with_CustomField|TC#8110} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "[date]");
			dataMap.put("State", "yes");
			dataMap.put("metaDataOption", "CustomField");
			dataMap.put("searchType", "is");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_with_full_text_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#196");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{full_text}{_with_full_text|TC#196} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"P Allen\" AND CustodianName: P Vinod\"");
			dataMap.put("searchType", "full_text");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_with_asterisk_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#171");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{full_text}{_with_asterisk|TC#171} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"P *\" AND CustodianName: P *\"");
			dataMap.put("searchType", "full_text");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_with_question_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#171");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{full_text}{_with_question|TC#171} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CustodianName: \"? Allen\" AND CustodianName: ? Vinod\"");
			dataMap.put("searchType", "full_text");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_with_full_text_generic_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#168");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{full_text}{_with_full_text_generic|TC#168} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("FullText", "CusotidanName: \"P Allen\" AND CustodianName: \"P Vinod\"");
			dataMap.put("searchType", "full_text");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_searchwith_long_query_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#167");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{long_search}{with_long_query|TC#167} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "P Allen");
			dataMap.put("F", "OR");
			dataMap.put("searchType", "long_search");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P Vinod");
			dataMap.put("metaDataOption1", "CustodianName");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_search_with_wildcard_asterisk_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#170");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{long_search}{_with_wildcard_asterisk|TC#170} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "P *");
			dataMap.put("F", "OR");
			dataMap.put("searchType", "long_search");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P *");
			dataMap.put("metaDataOption1", "CustodianName");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_search_with_wildcard_question_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#170");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{long_search}{_with_wildcard_question|TC#170} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "? Allen");
			dataMap.put("F", "OR");
			dataMap.put("searchType", "long_search");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "? Vinod");
			dataMap.put("metaDataOption1", "CustodianName");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_search_with_long_query_generic_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#167");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{long_search}{_with_long_query_generic|TC#167} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue1", "P Allen");
			dataMap.put("F", "OR");
			dataMap.put("searchType", "long_search");
			dataMap.put("metaDataOption2", "CustodianName");
			dataMap.put("metaDataValue2", "P Vinod");
			dataMap.put("metaDataOption1", "CustodianName");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_with_EmailSentDate_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8059");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{range}{_with_EmailSentDate|TC#8059} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("State", "yes");
			dataMap.put("MetaDataOption", "EmailSentDate");
			dataMap.put("MetaDataValue", "[date]");
			dataMap.put("MetaDataValue2", "[date2]");
			dataMap.put("searchType", "range");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_with_EmailSentDate_date_only_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8081");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{range}{_with_EmailSentDate_date_only|TC#8081} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("State", "yes");
			dataMap.put("MetaDataOption", "EmailSentDate");
			dataMap.put("MetaDataValue", "[date]");
			dataMap.put("MetaDataValue2", "[date2]");
			dataMap.put("searchType", "range");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_with_CustomField_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8111");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{range}{_with_CustomField|TC#8111} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("State", "yes");
			dataMap.put("MetaDataOption", "CustomField");
			dataMap.put("MetaDataValue", "[date]");
			dataMap.put("MetaDataValue2", "[date2]");
			dataMap.put("searchType", "range");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_with_Master_Date_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#8071");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{range}{_with_Master_Date|TC#8071} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("State", "yes");
			dataMap.put("MetaDataOption", "Master Date");
			dataMap.put("MetaDataValue", "[date]");
			dataMap.put("MetaDataValue2", "[date2]");
			dataMap.put("searchType", "range");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_EmailAuthorDomain_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#150");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_EmailAuthorDomain|TC#150} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailAuthorDomain");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_EmailRecipientNames_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#151");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_EmailRecipientNames|TC#151} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailRecipientNames");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_EmailSubject_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#169");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_EmailSubject|TC#169} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailSubject");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_EmailAllDomain_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#341");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_EmailAllDomain|TC#341} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "EmailAllDomain");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_DocFileExtension_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#2676");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_DocFileExtension|TC#2676} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", ".doc");
			dataMap.put("metaDataOption", "DocFileExtension");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_Master_Date_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#2679");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_Master_Date|TC#2679} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "[date]");
			dataMap.put("metaDataOption", "Master Date");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_DocFileType_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#152");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_DocFileType|TC#152} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "pdf");
			dataMap.put("metaDataOption", "DocFileType");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_custodianName_When_Then_verify_search_criteria() throws Throwable
	{
		HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#3396");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_custodianName|TC#3396} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "test");
			dataMap.put("metaDataOption", "CustodianName");
			dataMap.put("searchType", "MetaData");
			context.create_search(true, dataMap);
			context.verify_search_criteria(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
}//end

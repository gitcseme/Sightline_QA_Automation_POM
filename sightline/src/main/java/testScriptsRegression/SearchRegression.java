package testScriptsRegression;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.SearchContext;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked" })
public class SearchRegression extends RegressionBase {

	SearchContext context = new SearchContext();

/*
	public void test_Given_sightline_is_launched_and_login_When_goto_search_session_page_Then_on_production_Search_Session_page() throws Throwable
*/

	/* combined as part of this test case
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_previous_search_query_selection() throws Throwable
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_edit_previous_search_query() throws Throwable
	*/
	@Test(groups = {"Search", "Positive"})  // need to figure how to scroll save button on query save
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_saved_search_SEARCH5() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase", "84");
		ExtentTest test = report.startTest("Given verify_searched_save When  Then verify_current_login_session_previous_search_query_selection");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("nTimes", "5");
			ArrayList searches = new ArrayList();
			dataMap.put("searches", searches);
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("nTimes")));n++) {
				dataMap.put("metaDataOption","CustodianName");
				String searchString = String.format("\"searchString %s\"",(new Date()).getTime());
				dataMap.put("metaDataValue",searchString);
				context.create_search(true, dataMap);
				context.save_search(true, dataMap);
				context.verify_searched_save(true, dataMap);
				
				String searchName = (String) dataMap.get("searchName");
				HashMap search = new HashMap();
				search.put("name", searchName);
				search.put("search", searchString);
				searches.add(search);
			}
			//context.(true, dataMap);
			context.verify_current_login_session_previous_search_query_selection(true, dataMap);
			context.verify_current_login_session_edit_previous_search_query(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_verify_searched_save_and_modify_search_criteria_When_Then_verify_user_modified_session_query_not_changed_saved_query() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_searched_save and modify_search_criteria When  Then verify_user_modified_session_query_not_changed_saved_query");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
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

	/*
	public void test_Given_verify_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	*/
	@Test(groups = {"Search", "Positive", "smoke"})
	public void test_Given_on_production_Search_Session_page_and_create_searchForEach_MetaData_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[ForEach_MetaData] When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			dataMap.put("project","021320_EG");
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			//SessionSearch sessionSearch = (SessionSearch) dataMap.get("sessionSearch");
			//Element metaDataElement = sessionSearch.getMetaOption();
			//List<WebElement> metaDataOptions = metaDataElement.selectFromDropdown().getOptions();
			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailAuthorDomain");
			metaDataMap.put("value","enron.com");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","150");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailRecipientNames");
			metaDataMap.put("value","symes");
			metaDataMap.put("expectedPureHit","5");
			metaDataMap.put("TestCase","151");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","DocFileType");
			metaDataMap.put("value","document");
			metaDataMap.put("expectedPureHit","37");
			metaDataMap.put("TestCase","152");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","DocFileExtension");
			metaDataMap.put("value","pdf");
			metaDataMap.put("expectedPureHit","22");
			metaDataMap.put("TestCase","2976");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailSubject");
			metaDataMap.put("value","\"Subpoena Response Team\"");
			metaDataMap.put("expectedPureHit","1");
			metaDataMap.put("TestCase","169");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","CustodianName");
			metaDataMap.put("value","\"Erika*\"");
			metaDataMap.put("expectedPureHit","32");
			metaDataMap.put("TestCase","170");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","CustodianName");
			metaDataMap.put("value","\"Eri?a*\"");
			metaDataMap.put("expectedPureHit","32");
			metaDataMap.put("TestCase","170");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailAllDomains");
			metaDataMap.put("value","enron.com");
			metaDataMap.put("expectedPureHit","1052");
			metaDataMap.put("TestCase","341");
			metaDataOptions.add(metaDataMap);

			for (HashMap option : metaDataOptions) {
				dataMap.put("searchType",option.get("searchType"));
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("metaDataVal2",option.get("value2"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchForEach_MetaData_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search[ForEach_MetaData] When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			dataMap.put("project","021320_EG");
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			//SessionSearch sessionSearch = (SessionSearch) dataMap.get("sessionSearch");
			//Element metaDataElement = sessionSearch.getMetaOption();
			//List<WebElement> metaDataOptions = metaDataElement.selectFromDropdown().getOptions();
			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailRecipientNames");
			metaDataMap.put("value","enron.com");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","3124");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailRecipientAddresses");
			metaDataMap.put("value","symes");
			metaDataMap.put("expectedPureHit","5");
			metaDataMap.put("TestCase","3125");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metaData");
			metaDataMap.put("option","EmailRecipientAddresses");
			metaDataMap.put("value","document");
			metaDataMap.put("expectedPureHit","37");
			metaDataMap.put("TestCase","3126");
			metaDataOptions.add(metaDataMap);
			
			for (HashMap option : metaDataOptions) {
				dataMap.put("searchType",option.get("searchType"));
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("metaDataVal2",option.get("value2"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_advanced_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchRegex_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_searchRegex When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			dataMap.put("project","021320_EG");
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			//SessionSearch sessionSearch = (SessionSearch) dataMap.get("sessionSearch");
			//Element metaDataElement = sessionSearch.getMetaOption();
			//List<WebElement> metaDataOptions = metaDataElement.selectFromDropdown().getOptions();
			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("searchType","metadata");
			metaDataMap.put("option","EmailAuthorAddress");
			metaDataMap.put("value","\"##all\"");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","10303");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metadata");
			metaDataMap.put("option","EmailSubject");
			metaDataMap.put("value","(\"## Test mail on [0-9]{4}/[0-9]{2}/[0-9]{2}\"");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","10305");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metadata");
			metaDataMap.put("option","EmailSubject");
			metaDataMap.put("value","(\"##[0-9]{4}/[0-9]{2}/[0-9]{2}\"");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","10313");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","metadata");
			metaDataMap.put("option","EmailAuthorAddress");
			metaDataMap.put("value","(\"email@domain.com\"");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","10487");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","fulltext");
			metaDataMap.put("value","(\"email@domain.com\"");
			metaDataMap.put("expectedPureHit","1032");
			metaDataMap.put("TestCase","10484");
			metaDataOptions.add(metaDataMap);
			
			metaDataMap = new HashMap();
			metaDataMap.put("searchType","fulltext");
			metaDataMap.put("value","\"##0.375\"");
			metaDataMap.put("expectedPureHit","5");
			metaDataMap.put("TestCase","10298");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("searchType","fulltext");
			metaDataMap.put("value","\"##[09]{3}-[0-9]{3}-[0-9]{4}\"");
			metaDataMap.put("expectedPureHit","5");
			metaDataMap.put("TestCase","10299");
			metaDataOptions.add(metaDataMap);

			for (HashMap option : metaDataOptions) {
				dataMap.put("searchType",option.get("searchType"));
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("metaDataVal2",option.get("value2"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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

	/*
	@Test(groups = {"Search", "Positive", "smoke1"})
	public void test_Given_on_production_Search_Session_page_and_create_searchnTimes_When_save_search_Then_verify_searched_save() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[nTimes] When save_search Then verify_searched_save");

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
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

*/

	/*
	public void test_Given_verify_is_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	*/
	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchis_When_Then_verify_is_search_criteria() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[is] When  Then verify_is_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchType","IS");

			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("option","EmailAllDomainCount");
			metaDataMap.put("value","2");
			metaDataMap.put("expectedPureHit","9");
			metaDataMap.put("TestCase","153");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","NearDupeCount");
			metaDataMap.put("value","5");
			metaDataMap.put("expectedPureHit","6");
			metaDataMap.put("TestCase","340");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","MasterDate");
			metaDataMap.put("value","2019-06-07");
			metaDataMap.put("expectedPureHit","12");
			metaDataMap.put("TestCase","2679");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","EmailSentDate");
			metaDataMap.put("value","2020-02-18");
			metaDataMap.put("expectedPureHit","10");
			metaDataMap.put("TestCase","8058");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","EmailSentDate");
			metaDataMap.put("value","2020-02-18");
			metaDataMap.put("expectedPureHit","10");
			metaDataMap.put("TestCase","8080");
			metaDataOptions.add(metaDataMap);
			
			for (HashMap option : metaDataOptions) {
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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

	/*
	public void test_Given_verify_fulltext_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	*/
	
	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_search_When_Then_verify_fulltext_search_criteria() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[full_text_search] When  Then verify_fulltext_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			
			dataMap.put("searchType","FULLTEXT");

			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("value","\"Eri?a*\"");
			metaDataMap.put("expectedPureHit","32");
			metaDataMap.put("TestCase","171");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("value","Erika");
			metaDataMap.put("expectedPureHit","32");
			metaDataMap.put("TestCase","196");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("value","(CustodianName: \"P Allen\") OR (CustodianName: \"P Vinod\")");
			metaDataMap.put("expectedPureHit","152");
			metaDataMap.put("TestCase","168");
			metaDataOptions.add(metaDataMap);

			for (HashMap option : metaDataOptions) {
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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


	/*
	public void test_Given_verify_range_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	*/
	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_When_Then_verify_range_search_criteria() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search[ForEach_MetaData] When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			dataMap.put("project","021320_EG");
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			//SessionSearch sessionSearch = (SessionSearch) dataMap.get("sessionSearch");
			//Element metaDataElement = sessionSearch.getMetaOption();
			//List<WebElement> metaDataOptions = metaDataElement.selectFromDropdown().getOptions();

			dataMap.put("searchType","RANGE");

			List<HashMap> metaDataOptions= new ArrayList<HashMap>();
			HashMap metaDataMap = new HashMap();
			metaDataMap.put("option","MasterDate");
			metaDataMap.put("value","2019-06-07");
			metaDataMap.put("value2","2019-06-14");
			metaDataMap.put("expectedPureHit","14");
			metaDataMap.put("TestCase","8071");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","EmailSentDate");
			metaDataMap.put("value","2001-05-03");
			metaDataMap.put("value2","2001-06-11");
			metaDataMap.put("expectedPureHit","9");
			metaDataMap.put("TestCase","8059");
			metaDataOptions.add(metaDataMap);

			metaDataMap = new HashMap();
			metaDataMap.put("option","EmailSentDate");
			metaDataMap.put("value","2001-05-03");
			metaDataMap.put("value2","2001-06-11");
			metaDataMap.put("expectedPureHit","9");
			metaDataMap.put("TestCase","8081");
			metaDataOptions.add(metaDataMap);

			for (HashMap option : metaDataOptions) {
				dataMap.put("metaDataOption",option.get("option"));
				dataMap.put("metaDataValue",option.get("value"));
				dataMap.put("metaDataVal2",option.get("value2"));
				dataMap.put("expectedPureHit",option.get("expectedPureHit"));
				dataMap.put("TestCase",option.get("TestCase"));
				context.create_search(true, dataMap);
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);
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

	/*
	public void test_Given_on_production_Search_Session_page_and_create_searchlong_search_When_Then_verify_long_search_criteria() throws Throwable
	*/
	@Test(groups = {"Search", "Positive"})
	public void test_Given_verify_long_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given verify_long_search_criteria When click_search Then verify_search_returned");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			
			ArrayList longSearchList = new ArrayList();
			dataMap.put("longSearchList",longSearchList);
			
			HashMap searchEntryMap = new HashMap();
			searchEntryMap.put("entrySearchType", "metaData");
			searchEntryMap.put("metaDataOption", "CustodianName");
			searchEntryMap.put("metaDataValue", "\"P Allen\"");
			longSearchList.add(searchEntryMap);
			
			searchEntryMap = new HashMap();
			searchEntryMap.put("entrySearchType", "condition");
			searchEntryMap.put("condition", "OR");
			longSearchList.add(searchEntryMap);
			
			searchEntryMap = new HashMap();
			searchEntryMap.put("entrySearchType", "metaData");
			searchEntryMap.put("metaDataOption", "CustodianName");
			searchEntryMap.put("metaDataValue", "\"P Vinod\"");
			longSearchList.add(searchEntryMap);
			
			dataMap.put("expectedPureHit","152");
			dataMap.put("TestCase","167");

			dataMap.put("searchType","LONG");
			
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_select_advanced_search_with_CustodianName_When_Then_verify_autosuggest() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and select_advanced_search{_with_CustodianName} When  Then verify_autosuggest");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("advancedSearch", "yes");
			
			ArrayList<HashMap> searchList = new ArrayList<HashMap>();
			HashMap searchMap = new HashMap();
			searchMap.put("TestCase","11228");
			searchMap.put("metaDataOption","CustodianName");
			searchMap.put("metaDataValue","Eri");
			searchMap.put("additionalKeys", "xa ");
			searchList.add(searchMap);
			
			searchMap = new HashMap();
			searchMap.put("TestCase","11284");
			searchMap.put("metaDataOption","CustodianName");
			searchMap.put("metaDataValue","Eri");
			searchMap.put("additionalKeys", "ka X");
			searchList.add(searchMap);
			
			searchMap = new HashMap();
			searchMap.put("TestCase","11286");
			searchMap.put("metaDataOption","CustodianName");
			searchMap.put("metaDataValue","Eri");
			searchMap.put("additionalKeys", "@a");
			searchList.add(searchMap);
			
			searchMap = new HashMap();
			searchMap.put("TestCase","11287");
			searchMap.put("metaDataOption","CustodianName");
			searchMap.put("metaDataValue","Eri");
			searchMap.put("additionalKeys", " a");
			searchList.add(searchMap);
			
			for (HashMap searchMapItem : searchList) {	
				dataMap.put("TestCase",searchMapItem.get("TestCase"));
				dataMap.put("metaDataOption",searchMapItem.get("metaDataOption"));
				dataMap.put("metaDataValue",searchMapItem.get("metaDataValue"));
				dataMap.put("additionalKeys", searchMapItem.get("additionalKeys"));
				dataMap.put("advancedSearch", searchMapItem.get("advancedSearch"));
				context.select_advanced_search(true, dataMap);
				context.verify_autosuggest(false, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_select_search_with_metaDataValue_causes_autosuggest_When_Then_verify_autosuggest() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest("Given on_production_Search_Session_page and select_search{_with_metaDataValue_causes_autosuggest|TC#5708} When  Then verify_autosuggest");

		dataMap.put("TestCase","5708");
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(false, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataOption","CustodianName");
			dataMap.put("metaDataValue","Eri");
			dataMap.put("additionalKeys", "ka ");
			dataMap.put("additionalKeys1", "Gra");
			dataMap.put("additionalKeys2", "jed");
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_cancel_save_search_Then_verify_search_not_saved() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","410");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search When cancel_save_search Then verify_search_not_saved");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"Erika Grajeda\"");
			
			context.create_search(true, dataMap);
			context.cancel_save_search(true, dataMap);
			context.verify_search_not_saved(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	// Proximity Test cases
	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_warning_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10240");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_wildcard_warning|TC#10240} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchValue", "\"Agile Practice*\"~6");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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

/*
	public void test_Given_on_production_Search_Session_page_When_Then_verify_search_returned() throws Throwable
*/

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10268");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_simple_regex|TC#10268} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchValue", "\"(\"##[a-z]{5}\") Practice\"~3");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10270");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_simple_regex_hyphen|TC#10270} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "\"##[a-z]{3}-[a-z]{9} Certification*\"~7");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11549");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_or|TC#11549} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "c0mpany OR (\"(\"##[a-z]{5}\") Practice\"~3 OR m0ney) OR TruthF1nder");
			dataMap.put("expectedPureHit", "1");
			dataMap.put("searchType", "Content");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11550");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_and|TC#11550} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "\"Agile Practices\"~6 AND Services");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11551");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_not|TC#11551} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "(\"Agile Practices\"~6 AND Services) NOT M0ney");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11552");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_regex|TC#11552} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "c0mpany OR (\"(\"##[a-z]{5}\") Practice\"~3 OR m0ney) OR TruthF1nder");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11553");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_boolean_regex_combo|TC#11553} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "\"(SmartSearch  OR  develope*) (\"##precis?on.\"  OR  w?ere   OR  m?ne*)\"~10");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","9601");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity|TC#9601} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchValue", "\"Agile Practices\"~6");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "14");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10521");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_search{MetaData}{_with_proximity_wildcard|TC#10521} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			dataMap.put("searchValue", "\"Agile Practice*\"~6");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "22");
			context.create_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10267");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_simple_regex|TC#10267} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchValue", "\"(\"##[a-z]{5}\") practices\"~7");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_advanced_search(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","10269");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_simple_regex_hyphen|TC#10269} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("searchValue", "\"##[a-z]{3}-[a-z]{9} Certification*\"~7");
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_advanced_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11554");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_or|TC#11554} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "(\"agile practices\"~7 OR core) OR m0ney");
			
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "22");
			context.create_advanced_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11555");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_and|TC#11555} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "(\"agile practices\"~7 AND core) OR m0ney");
			
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "14");
			context.create_advanced_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","TC#11556");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_not|TC#11556} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "(\"agile practices\"~7 OR core) NOT m0ney");
			
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "22");
			context.create_advanced_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_returned() throws Throwable
	{
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11557");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_regex|TC#11557} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"(SmartSea* Reca*) ((\"##[a-z]{8}\") develope*)\"~20");
			
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "17");
			context.create_advanced_search(true, dataMap);
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


	@Test(groups = {"Search", "Positive"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_returned() throws Throwable {
		//HashMap dataMap = new HashMap();

		dataMap.put("TestCase","11558");
		ExtentTest test = report.startTest("Given on_production_Search_Session_page and create_advanced_search{MetaData}{_with_proximity_boolean_regex_combo|TC#11558} When  Then verify_search_criteria");

		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			dataMap.put("pwd", "Q@test_10");
			dataMap.put("uid", "qapau4@consilio.com");
			context.login(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			dataMap.put("metaDataValue", "\"(SmartSearch  OR  develope*) (\"##precis?on.\"  OR  w?ere   OR  m?ne*)\"~10");
			
			dataMap.put("searchType", "Content");
			dataMap.put("expectedPureHit", "1");
			context.create_advanced_search(true, dataMap);
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
} //EOF


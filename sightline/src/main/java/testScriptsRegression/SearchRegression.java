package testScriptsRegression;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
	@Test(groups = {"Search", "Positive", "Smoke"})  // need to figure how to scroll save button on query save
	public void test_Given_verify_searched_save_When_Then_verify_current_login_session_saved_search_SEARCH5() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
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
			//context.verify_proper_message_invalid_save_name(true,dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Search", "Positive", "WIP"})
	public void test_Given_verify_searched_save_and_modify_search_criteria_When_Then_verify_user_modified_session_query_not_changed_saved_query() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
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

	@Test(groups = {"Search", "Positive"})  
	public void test_Given_create_search_and_pin_search_and_create_multiple_search_When_Then_verify_first_search_pin() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			JSONArray logins = (JSONArray) dataMap.get("logins");
			Iterator<JSONArray> loginsList = logins.iterator();
			try {
				while (loginsList.hasNext()) {
					JSONArray loginDataList = loginsList.next();
					Iterator<JSONObject> loginData = loginDataList.iterator();
					while (loginData.hasNext()) {
						JSONObject data = loginData.next();
						dataMap.put(data.get("name"), data.get("value"));
					}
								
					context.login_as(true, dataMap);
					context.select_project(true, dataMap);
					context.goto_search_session_page(true, dataMap);
					context.on_production_Search_Session_page(true, dataMap);
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.pin_search(true,dataMap);
					//remove this TEST ONLY
					dataMap.put("mutipleSearches","3");
					for (int n=0;n < Integer.parseInt(((String)dataMap.get("mutipleSearches")));n++) {
						context.create_search(true, dataMap);
						context.click_search(true, dataMap);
						context.verify_search_returned(true, dataMap);			
					}
					context.verify_first_search_pin(true, dataMap);
					context.logoff(true, dataMap);
				} 
			} catch (ImplementationException e) {
				test.log(LogStatus.SKIP, e.getMessage());
				Assert.assertTrue(e.getMessage(), false);;
			} catch (Exception le) {
				test.log(LogStatus.FATAL, le.getMessage());
				Assert.assertTrue(le.getMessage(), false);;
			}
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Search", "Positive"})  
	public void test_Given_create_advanced_search_and_pin_search_and_create_multiple_advanced_search_When_Then_verify_first_search_pin() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			context.create_advanced_search(true, dataMap);
			context.click_search(true, dataMap);
			context.verify_search_returned(true, dataMap);
			context.pin_search(true,dataMap);
			for (int n=0;n < Integer.parseInt(((String)dataMap.get("mutipleSearches")));n++) {
				context.create_advanced_search(true, dataMap);			
				context.click_search(true, dataMap);
				context.verify_search_returned(true, dataMap);	
			}
			context.verify_first_search_pin(true, dataMap);
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_Then_verify_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.verify_search_criteria(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchForEach_MetaData_When_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchForEach_MetaData_When_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchForEach_MetaData_and_select_advanced_search_options_When_Then_verify_advanced_search_options_notice() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.select_advanced_search_options(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_advanced_search_options_notice(true, dataMap);
					//context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchRegex_When_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchis_When_Then_verify_is_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchis_When_Then_verify_is_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchfull_text_search_When_Then_verify_fulltext_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchfull_text_search_When_Then_verify_fulltext_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchrange_When_Then_verify_range_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchrange_When_Then_verify_range_search_criteria() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("metaDataOptions");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_verify_long_search_criteria_When_click_search_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			//			JSONArray metaDataOptions = (JSONArray) dataMap.get("longSearchList");
			//     	    Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			//	        while (optionsList.hasNext()) {
			//	        	JSONArray option = optionsList.next();
			//	     	    Iterator<JSONObject> iterator = option.iterator();
			//		        while (iterator.hasNext()) {
			//		        	JSONObject data = iterator.next();
			//		        	dataMap.put(data.get("name"), data.get("value"));
			//		        }
			//			}

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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_select_advanced_search_with_CustodianName_When_Then_verify_autosuggest() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray metaDataOptions = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.select_advanced_search(true, dataMap);
					context.verify_autosuggest(false, dataMap);
					context.cancel_metadata_insert(true,dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_select_metaData_When_Then_verify_tooltip() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.select_metadata(true, dataMap);
					context.verify_tooltip(true, dataMap);
					context.cancel_metadata_insert(true,dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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


	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_select_search_with_metaDataValue_causes_autosuggest_When_Then_verify_autosuggest() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.select_search(true, dataMap);
					context.verify_autosuggest(true, dataMap);
					context.cancel_metadata_insert(true,dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_not_select_search_with_metaDataValue_causes_autosuggest_When_Then_not_verify_autosuggest() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(false, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);
			JSONArray metaDataOptions = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = metaDataOptions.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.select_search(true, dataMap);
					context.verify_autosuggest(false, dataMap);
					context.cancel_metadata_insert(true,dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_cancel_save_search_Then_verify_search_not_saved() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

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
	/*
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_warning_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_searchMetaData_with_proximity_wildcard_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_simple_regex_hyphen_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_or_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_and_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_not_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_When_Then_verify_search_returned() throws Throwable
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetaData_with_proximity_boolean_regex_combo_When_Then_verify_search_returned() throws Throwable {
	*/
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_with_proximity_When_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("proximitySearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_with_proximity_When_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("proximitySearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_search_returned(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_searchMetadata_When_Then_verify_error_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.verify_error_message(true, dataMap);
					context.cancel_metadata_insert(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_searchMetadata_When_Then_verify_error_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.verify_error_message(true, dataMap);
					context.cancel_metadata_insert(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_click_search_Then_verify_wrong_query_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_wrong_query_message(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_click_search_Then_verify_wrong_query_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_wrong_query_message(true, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_click_search_Then_verify_wrong_query_message_and_verify_pure_hit_results() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_wrong_query_message(true, dataMap);
					context.verify_search_returned(true, dataMap);
					//context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_click_search_Then_verify_wrong_query_message_and_verify_pure_hit_results() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("invalidSearchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.verify_wrong_query_message(true, dataMap);
					context.verify_search_returned(true, dataMap);
					//context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_click_search_Then_not_verify_wrong_query_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_search(true, dataMap);
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_wrong_query_message(false, dataMap);
					context.verify_search_returned(true, dataMap);
					//context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_click_search_Then_not_verify_wrong_query_message() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_wrong_query_message(false, dataMap);
					context.remove_search_criteria(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_comment_search_When_click_search_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_comment_search(true, dataMap);
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_search_returned(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_comment_search_When_click_search_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_comment_search(true, dataMap);
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_search_returned(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_search_When_click_search_Then_verify_tile_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			JSONArray logins = (JSONArray) dataMap.get("logins");
			Iterator<JSONArray> loginsList = logins.iterator();
			try {
				while (loginsList.hasNext()) {
					JSONArray loginDataList = loginsList.next();
					Iterator<JSONObject> loginData = loginDataList.iterator();
					while (loginData.hasNext()) {
						JSONObject data = loginData.next();
						dataMap.put(data.get("name"), data.get("value"));
					}
					context.login_as(true, dataMap);
					context.select_project(true, dataMap);
					context.goto_search_session_page(true, dataMap);
					context.on_production_Search_Session_page(true, dataMap);
		
					JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
					Iterator<JSONArray> optionsList = proximitySearchList.iterator();
					while (optionsList.hasNext()) {
						JSONArray option = optionsList.next();
						Iterator<JSONObject> iterator = option.iterator();
						while (iterator.hasNext()) {
							JSONObject data = iterator.next();
							dataMap.put(data.get("name"), data.get("value"));
						}
						try {
							context.create_search(true, dataMap);
							context.click_search(true, dataMap);
							context.wait_for_loading(true, dataMap);
							context.verify_search_returned(true, dataMap);
						} catch (Exception e) {
							context.error(dataMap, e.getMessage());
							//e.printStackTrace();
		
							//try to recover by going back to Search page
							// continue with the next test case
							context.on_production_Search_Session_page(true, dataMap);	    
						}
					}
					context.logoff(true, dataMap);
				} 
			} catch (ImplementationException e) {
				test.log(LogStatus.SKIP, e.getMessage());
				Assert.assertTrue(e.getMessage(), false);;
			} catch (Exception le) {
				test.log(LogStatus.FATAL, le.getMessage());
				Assert.assertTrue(le.getMessage(), false);;
			}
		} catch (Exception e) {
			test.log(LogStatus.FATAL, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);;
		} finally { 
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_click_search_Then_verify_tile_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray proximitySearchList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> optionsList = proximitySearchList.iterator();
			while (optionsList.hasNext()) {
				JSONArray option = optionsList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.create_advanced_search(true, dataMap);
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_search_returned(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_start_advanced_search_and_create_workproduct_search_When_click_search_Then_verify_search_returned() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			JSONArray searchesList = (JSONArray) dataMap.get("searchList");
			Iterator<JSONArray> searchList = searchesList.iterator();
			while (searchList.hasNext()) {
				JSONArray option = searchList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					context.start_advanced_search(true, dataMap);
					JSONArray workProductTypes = (JSONArray) dataMap.get("workProductTypes");
					Iterator<JSONArray> typesList = workProductTypes.iterator();
					while (typesList.hasNext()) {
						JSONArray workProductType = (JSONArray) typesList.next();
						Iterator<JSONObject> typeList = workProductType.iterator();
						while (typeList.hasNext()) {
							JSONObject data = typeList.next();
							dataMap.put(data.get("name"), data.get("value"));
						}
						context.create_workproduct_search(true, dataMap);
					}
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.verify_search_returned(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_and_plus_icon_add_to_shopping_cart_When_click_Then_verify_added_tiles_in_shopping_cart() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			Iterator<JSONArray> searchList = context.getIterator(dataMap, "searchList");
			while (searchList.hasNext()) {
				JSONArray tilesAddedToShoppingCart = new JSONArray();
				dataMap.put("tilesAddedToShoppingCart", tilesAddedToShoppingCart);

				JSONArray option = searchList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					String advancedSearch = (String) dataMap.get("advancedSearch");
					if (advancedSearch != null && advancedSearch.equalsIgnoreCase("Yes")) {
						context.create_advanced_search(true, dataMap);
					} else {
						context.create_search(true, dataMap);
					}
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.plus_icon_add_to_shopping_cart(true, dataMap);
					
					context.verify_added_tiles_in_shopping_cart(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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

	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_When_Then_verify_plus_icon_not_possible_while_search_loading() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			Iterator<JSONArray> searchList = context.getIterator(dataMap, "searchList");
			while (searchList.hasNext()) {
				JSONArray tilesAddedToShoppingCart = new JSONArray();
				dataMap.put("tilesAddedToShoppingCart", tilesAddedToShoppingCart);

				JSONArray option = searchList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					String advancedSearch = (String) dataMap.get("advancedSearch");
					if (advancedSearch != null && advancedSearch.equalsIgnoreCase("Yes")) {
						context.create_advanced_search(true, dataMap);
					} else {
						context.create_search(true, dataMap);
					}
					context.click_search(true, dataMap);
					context.verify_plus_icon_not_possible_while_search_loading(true, dataMap);
					context.wait_for_loading(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
	
	@Test(groups = {"Search", "Positive", "Regression"})
	public void test_Given_on_production_Search_Session_page_and_create_advanced_search_and_plus_icon_add_to_shopping_cart_When_unpin_tiles_in_shopping_cart_click_Then_verify_tiles_sequence_in_results() throws Throwable
	{
		String methodName = new Throwable() 
				.getStackTrace()[0] 
						.getMethodName(); 
		getMethodData(dataMap,methodName);

		ExtentTest test = report.startTest(methodName);
		dataMap.put("ExtentTest",test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login(true, dataMap);
			context.select_project(true, dataMap);
			context.goto_search_session_page(true, dataMap);
			context.on_production_Search_Session_page(true, dataMap);

			Iterator<JSONArray> searchList = context.getIterator(dataMap, "searchList");
			while (searchList.hasNext()) {
				JSONArray tilesAddedToShoppingCart = new JSONArray();
				dataMap.put("tilesAddedToShoppingCart", tilesAddedToShoppingCart);

				JSONArray option = searchList.next();
				Iterator<JSONObject> iterator = option.iterator();
				while (iterator.hasNext()) {
					JSONObject data = iterator.next();
					dataMap.put(data.get("name"), data.get("value"));
				}
				try {
					String advancedSearch = (String) dataMap.get("advancedSearch");
					if (advancedSearch != null && advancedSearch.equalsIgnoreCase("Yes")) {
						context.create_advanced_search(true, dataMap);
					} else {
						context.create_search(true, dataMap);
					}
					context.click_search(true, dataMap);
					context.wait_for_loading(true, dataMap);
					context.plus_icon_add_to_shopping_cart(true, dataMap);
					context.unpin_tiles_in_shopping_cart(true, dataMap);
					
					context.verify_tiles_sequence_in_results(true, dataMap);
				} catch (Exception e) {
					context.error(dataMap, e.getMessage());
					//e.printStackTrace();

					//try to recover by going back to Search page
					// continue with the next test case
					context.on_production_Search_Session_page(true, dataMap);	    
				}
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
} //EOF


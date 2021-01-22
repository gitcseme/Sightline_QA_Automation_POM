package testScriptsRegression;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import junit.framework.Assert;
import stepDef.ImplementationException;
import stepDef.BatchRedactionContext;


@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class BatchRedactionRegression extends RegressionBase {

	WebDriver webDriver;
	BatchRedactionContext context = new BatchRedactionContext();
	
	@Test(groups = {"BatchReduction", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_rmu_When_on_redaction_home_page_Then_verify_title()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given_sightline_is_launched_and_login_as_rmu_When_on_redaction_home_page_Then_verify_title");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_rmu(true, dataMap);
			context.on_redactions_home_page(true, dataMap);
			context.verify_title_returned(true, dataMap);

		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	
	@Test(groups = {"BatchReduction", "Negative", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_dau_Then_Impersonate_as_rmu_and_verify_redaction_home_page()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given_sightline_is_launched_and_login_as_dau_Then_Impersonate_as_rmu_and_verify_redaction_home_page");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_dau(true, dataMap);
			context.impersonate_rmu(true, dataMap);
			context.on_redactions_home_page(true, dataMap);
			context.verify_title_returned(true, dataMap);
			
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}

	@Test(groups = {"BatchReduction", "Positive", "Regression"})
	public void test_Given_sightline_is_launched_and_login_as_rmuu_Then_click_view_and_redact_and_verify_default_selection()
			throws Throwable {
		HashMap dataMap = new HashMap();

		ExtentTest test = report.startTest(
				"Given_sightline_is_launched_and_login_as_rmu_Then_click_view_and_redact_and_verify_default_selection");
		dataMap.put("ExtentTest", test);

		try {
			context.sightline_is_launched(true, dataMap);
			context.login_as_rmu(true, dataMap);
			context.on_redactions_home_page(true, dataMap);
			context.click_View_and_Redact(true, dataMap);
			
		} catch (ImplementationException e) {
			test.log(LogStatus.SKIP, e.getMessage());
			Assert.assertTrue(e.getMessage(), false);
			;
		} finally {
			context.close_browser(true, dataMap);
		}

		report.endTest(test);
	}
	}

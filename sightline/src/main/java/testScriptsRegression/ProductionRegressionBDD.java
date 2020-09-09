package testScriptsRegression;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.testng.TestNGCourgette;
import cucumber.api.CucumberOptions;

import testScriptsSmoke.Input;

@Test
@CourgetteOptions( 
		threads = 10, 
		runLevel = CourgetteRunLevel.SCENARIO, 
		rerunFailedScenarios = false, 
		showTestOutput = true,
		reportTargetDir = "build", 
		cucumberOptions = @CucumberOptions( 
				features = "src/test/resources/features", 
				glue = "stepDef" 
				//, plugin = { "extentreports" }

				, plugin = { 
                        "pretty",
                        "json:target/cucumber-report/cucumber.json",
                        "html:target/cucumber-report/cucumber.html"}
				)
		) 

public class ProductionRegressionBDD extends TestNGCourgette {
	static ExtentReports report;
	static ExtentTest test;
	static HashMap databaseMap;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeClass(alwaysRun = true)
	public static void startTest() {
		Input input = new Input();
		try {
			input.loadEnvConfig();
		} catch (ParseException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		report = new ExtentReports(System.getProperty("user.dir")+File.separator+"report"+File.separator+"ExtentReportResults.html");
		test = report.startTest("Consilio Regression Testing");	
		
		databaseMap = new HashMap();
		databaseMap.put("URL","http://mtpvtsslwb01.consilio.com/");
		databaseMap.put("ExtentReport",report);
		databaseMap.put("ExtentTest",test);
	}

	@AfterClass(alwaysRun = true)
	public void close(){
//		context.close_browser(true, databaseMap);
		report.endTest(test);
		report.flush();
	}

}
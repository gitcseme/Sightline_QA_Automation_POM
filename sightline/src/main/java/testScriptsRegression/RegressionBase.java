package testScriptsRegression;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;

import testScriptsSmoke.Input;

public class RegressionBase {
	
	static ExtentReports report;
	
	@BeforeSuite(alwaysRun = true)
	public static void start() {
		report = new ExtentReports(System.getProperty("user.dir")+File.separator+"report"+File.separator+"ExtentReportResults_"+System.currentTimeMillis()+".html", false);
	}
	@BeforeTest(alwaysRun = true)
	public static void startTest() {
		Input input = new Input();
		try {
			input.loadEnvConfig();
		} catch (ParseException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterTest(alwaysRun = true)
	public void endTest(){
		
	}

	@AfterSuite(alwaysRun = true)
	public void end(){
		report.flush();
	}

}
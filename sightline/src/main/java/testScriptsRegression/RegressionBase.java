package testScriptsRegression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;

import testScriptsSmoke.Input;

public class RegressionBase {
	
	static ExtentReports report;
	static ArrayList<HashMap> testCaseResultList = new ArrayList<HashMap>();
	static HashMap dataMap;
	
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
		dataMap = new HashMap();
		dataMap.put("TestCaseResults", testCaseResultList);
	}

	@AfterTest(alwaysRun = true)
	public void endTest(){
		
	}

	@AfterSuite(alwaysRun = true)
	public void end(){
		report.flush();
		
		//generate MTM log file
	    try {
	        FileWriter myWriter = new FileWriter(System.getProperty("user.dir")+File.separator+"log"+File.separator+"MTMResults_"+System.currentTimeMillis()+".csv");
	        myWriter.write("tid\tresult\tdescription\n");
	        for (HashMap resultMap : testCaseResultList) {
	        	String tid = (String) resultMap.get("tid");
	        	String result = (String) resultMap.get("result");
	        	String description = (String) resultMap.get("description");
	        	        	
	        	myWriter.write(String.format("%s\t%s\t%s\n",tid,result,description));
	        }
	        myWriter.close();
	        System.out.println("Successfully wrote MTM log file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred writing MTM log file.");
	        e.printStackTrace();
	      }

	}

}
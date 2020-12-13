package testScriptsRegression;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.testng.annotations.BeforeSuite;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    public void getMethodData(HashMap dataMap, String methodName) {
        JSONParser parser = new JSONParser();
        String env = Input.config.env;
        String projectName = Input.projectName;
        try {
           Object obj = parser.parse(new FileReader(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"configsAndTestData"+File.separator+"TestData_"+env+".json"));
           JSONObject jsonObject = (JSONObject)obj;
           try {
        	   JSONObject projectJson = (JSONObject)jsonObject.get(projectName);
        	   dataMap.put("project", projectName);
	           JSONArray list = (JSONArray) projectJson.get("variables");
	           if (list != null) {
		           Iterator<JSONObject> iterator = list.iterator();
		           while (iterator.hasNext()) {
		        	   JSONObject v = iterator.next();
		        	   dataMap.put(v.get("name"), v.get("value"));
		           }       
	           }
	           list = (JSONArray)projectJson.get(methodName);
	           if (list != null) {
	        	   Iterator<JSONObject> iterator = list.iterator();
		           while (iterator.hasNext()) {
		        	   JSONObject v = iterator.next();
		        	   dataMap.put(v.get("name"), v.get("value"));
		           }  
	           }
           } catch(Exception e) {
               throw new Exception(String.format("Project '%s' undefined for '%s' environment json",projectName,env));
           }    	
        } catch(Exception e) {
           e.printStackTrace();
        }    	
    }

}
package executionMaintenance;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;

import testScriptsSmoke.Input;


public class TestListener implements ITestListener {
	
	static String API_GET_EXECUTIONS = "{SERVER}/public/rest/api/1.0/executions/search/cycle/";
	static String API_GET_CYCLES = "{SERVER}/public/rest/api/1.0/cycles/search?";
	static String API_UPDATE_EXECUTION = "{SERVER}/public/rest/api/1.0/execution/";
	// Delimiter used in CSV file
	static final String NEW_LINE_SEPARATOR = "\n";
	static final String fileName = "F:\\cycleExecutionReport.csv";
	static String API_GETCURRENTEXECUTIONS = "{SERVER}/public/rest/api/1.0/execution/";
	
//	public static String versionId = "-1";
//	public static String projectId = "10000";
//	public static String projectName = "ZTEST";
//	public static String versionName = "Unscheduled";
//	public static String cycleName = "Test Cycle";
//	public static String cycleDescription = "Created by ZAPI CLOUD API";
//	public static String cycleID = "";
//	
//	static String zephyrBaseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
//	// zephyr accessKey , we can get from Addons >> zapi section
//	static String accessKey = "MDMyNDM3NjMtZDViMi0zZTYyLWEwYTktNDk1Y2M0ZDZiMmJkIDYxZWZhM2M0NzhiN2ZkMDA3MmQ3MmMzNSBVU0VSX0RFRkFVTFRfTkFNRQ";
//	// zephyr secretKey , we can get from Addons >> zapi section
//	static String secretKey = "lrtlquTOgzHQhuiFgLPybeBlNDDCHrwsx0mXbW_b9Xo";
//	// Jira accountId
//	static String accountId = "61efa3c478b7fd0072d72c35";
	
	public static String versionId = "13099";
	public static String projectId = "10500";
	public static String projectName = "RPMXCON";
	public static String versionName = "Indium";
	public static String cycleName = "Test Cycle";
	public static String cycleDescription = "Created by ZAPI CLOUD API";
	public static String cycleID = "b2e24bbb-34b4-4070-bbad-cb17ae390978";
	static String zephyrBaseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
	static String accessKey = "amlyYToxNjU1NTc2OSA2MGY4Mjk5MmYwMjZhYjAwNzAyZmM5OGMgVVNFUl9ERUZBVUxUX05BTUU";
	static String secretKey = "gO795XjYNimdmFcm-CbwQQCD5s9aQSV0igYZ4XLBNic";
	static String accountId = "60f82992f026ab00702fc98c";
	
	static String createCycleUri = zephyrBaseUrl + "/public/rest/api/1.0/cycle";
	
	static ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, accountId).build();
	
	static Map<String, String> executionIds = new HashMap<String, String>();

    @Override
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
		if(!context.getName().equalsIgnoreCase("Input")) {
		List<String> issues = new ArrayList<String>();
    	ITestNGMethod[] allTestMethods = context.getAllTestMethods();
    	int tcCount = allTestMethods.length;
    	for(ITestNGMethod i : allTestMethods)
    	{
    		System.out.println("Test Methods :"+i.getDescription());
    		issues.add(i.getDescription());
    		
    	}
    	System.out.println(Arrays.asList(issues));
    	
    	Date date = new Date();
    	String modifiedDate= new SimpleDateFormat("yyyyMMddhhmmss").format(date);
    	
    	
    	cycleName =context.getName() + " - "+modifiedDate;
    	
    	try {
    		
    		/** Cycle Object created - DO NOT EDIT **/
    		JSONObject createCycleObj = new JSONObject();
    		createCycleObj.put("name", cycleName);        			
    		createCycleObj.put("description", cycleDescription);			
    		createCycleObj.put("projectId", projectId);
    		createCycleObj.put("versionId", versionId);

    		StringEntity cycleJSON = null;
    		try {
    			cycleJSON = new StringEntity(createCycleObj.toString());
    		} catch (UnsupportedEncodingException e1) {
    			e1.printStackTrace();
    		}
    		
			cycleID = ZapiScripts.createCycle(createCycleUri, client, accessKey, cycleJSON);
			System.out.println("Cycle Created with Cycle Id :" + cycleID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try
    	{
    		String addTestsUri = zephyrBaseUrl + "/public/rest/api/1.0/executions/add/cycle/" + cycleID;
    		JSONArray issueIds = new JSONArray(issues); //Issue Id's to be added to Test Cycle, add more issueKeys separated by comma

    		JSONObject addTestsObj = new JSONObject();
    		addTestsObj.put("issues", issueIds);
    		addTestsObj.put("method", "1");
    		addTestsObj.put("projectId", projectId);
    		addTestsObj.put("versionId", versionId);

    		StringEntity addTestsJSON = null;
    		try {
    			addTestsJSON = new StringEntity(addTestsObj.toString());
    		} catch (UnsupportedEncodingException e1) {
    			e1.printStackTrace();
    		}
    		String ID = ZapiScripts.addTestsToCycle(addTestsUri, client, accessKey, addTestsJSON);
    		System.out.println("Tests added successfully  "+ID);
    		Thread.sleep(5000);
    	}
          catch(Exception e)
    	{
        	// TODO Auto-generated catch block
  			e.printStackTrace();
    	}
    	
    	final String getExecutionsUri = API_GET_EXECUTIONS.replace("{SERVER}", zephyrBaseUrl) + cycleID + "?projectId="
				+ projectId + "&versionId=" + versionId;
    	try {
    		int offset=0;
    		for(;offset<=tcCount;) {
			executionIds.putAll(ZapiScripts.getExecutionsByCycleId(getExecutionsUri, client, accessKey, tcCount,offset));
			offset=offset+50;
    		}
			System.out.println(executionIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

    @Override
	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

    @Override
	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());		
	}

    @Override        
    public void onTestSuccess(ITestResult Result)                    
    {        
        System.out.println("The name of the testcase passed is :"+Result.getName());    
        System.out.println("attribute is :"+Result.getMethod().getDescription());
        String issueID = Result.getMethod().getDescription();
        String execId = null;
      
        
        execId = executionIds.get(issueID).split(";")[1];

        String JIRAissueID = executionIds.get(issueID).split(";")[0];
        String getStatusURI = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + execId + "?issueId="+JIRAissueID+"&projectId="+projectId;
        String status = "";
        try {
            status = ZapiScripts.getCurrentExecution(getStatusURI,client,accessKey);
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        if(!status.equals("FAIL"))
        {
            JSONObject statusObj = new JSONObject();
            statusObj.put("id", "1"); //Failure : 2
 
            JSONObject executeTestsObj = new JSONObject();
            executeTestsObj.put("status", statusObj);
            executeTestsObj.put("cycleId", cycleID);
            executeTestsObj.put("projectId", projectId); 
            executeTestsObj.put("versionId", versionId);
            //executeTestsObj.put("comment", "Executed by ZAPI Cloud");
 
            final String updateExecutionUri = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + execId;
            // System.out.println(updateExecutionUri);
            // System.out.println(executionIds.get(key));
            executeTestsObj.put("issueId", executionIds.get(issueID).split(";")[0]);
            // System.out.println(executeTestsObj.toString());
            StringEntity executeTestsJSON = null;
            try {
                executeTestsJSON = new StringEntity(executeTestsObj.toString());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
 
            try {
                ZapiScripts.updateExecutions(updateExecutionUri, client, accessKey, executeTestsJSON);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }    
	
    @Override
	public void onTestFailure(ITestResult result) {
					
			System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
//			ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
//			ExtentTestManager.getTest().log(Status.FAIL,"TEST CASE FAILED IS " + result.getThrowable().getMessage());
		
//			System.out.println("Verify whether this is a bug: "+ Input.logJiraTicket);
//		if(Input.logJiraTicket) {
//			
//			JiraServiceProvider jiraSp = new JiraServiceProvider(Input.jiraUrl,Input.jiraUserName,Input.jiraToken,Input.jiraProject);
//			String issueSummary = result.getMethod().getConstructorOrMethod().getMethod() + "Got failed due to some automation assertion or exception";
//			String issueDescription =result.getThrowable().getMessage()+"\n";
//			issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
//			
//			jiraSp.createJiraTicket("Bug", issueSummary, issueDescription, "Srinivas Anand");
			//}
			
			String issueID = result.getMethod().getDescription();

	    	JSONObject statusObj = new JSONObject();
	    	statusObj.put("id", "2"); //Failure : 2

	    	JSONObject executeTestsObj = new JSONObject();
	    	executeTestsObj.put("status", statusObj);
	    	executeTestsObj.put("cycleId", cycleID);
	    	executeTestsObj.put("projectId", projectId);
	    	executeTestsObj.put("versionId", versionId);
	    	//executeTestsObj.put("comment", "Executed by ZAPI Cloud");

	    	String execId = executionIds.get(issueID).split(";")[1];
	    	final String updateExecutionUri = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + execId;
	    	// System.out.println(updateExecutionUri);
	    	// System.out.println(executionIds.get(key));
	    	executeTestsObj.put("issueId", executionIds.get(issueID).split(";")[0]);
	    	// System.out.println(executeTestsObj.toString());
	    	StringEntity executeTestsJSON = null;
	    	try {
	    		executeTestsJSON = new StringEntity(executeTestsObj.toString());
	    	} catch (UnsupportedEncodingException e1) {
	    		e1.printStackTrace();
	    	}

	    	try {
	    		ZapiScripts.updateExecutions(updateExecutionUri, client, accessKey, executeTestsJSON);
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
			
		}		
	
    @Override
	public void onTestSkipped(ITestResult result) {
		
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
		
	}

    @Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
	
}

package executionMaintenance;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;



public class ZapiScripts {
	
	static String API_GET_EXECUTIONS = "{SERVER}/public/rest/api/1.0/executions/search/cycle/";
	static String API_GET_CYCLES = "{SERVER}/public/rest/api/1.0/cycles/search?";
	private static String API_UPDATE_EXECUTION = "{SERVER}/public/rest/api/1.0/execution/";
	// Delimiter used in CSV file
	static final String NEW_LINE_SEPARATOR = "\n";
	static final String fileName = "F:\\cycleExecutionReport.csv";
	static String API_GETCURRENTEXECUTIONS = "{SERVER}/public/rest/api/1.0/execution/";
	

	
	
	public static String versionId = "13099";
	public static String projectId = "10500";
	public static String projectName = "RPMXCON";
	public static String versionName = "Indium";
	public static String cycleName = "Test Cycle";
	public static String cycleDescription = "Created by ZAPI CLOUD API";
	public static String cycleId = "b2e24bbb-34b4-4070-bbad-cb17ae390978";
	static String zephyrBaseUrl = "https://prod-api.zephyr4jiracloud.com/connect";
	static String accessKey = "amlyYToxNjU1NTc2OSA2MGY4Mjk5MmYwMjZhYjAwNzAyZmM5OGMgVVNFUl9ERUZBVUxUX05BTUU";
	static String secretKey = "gO795XjYNimdmFcm-CbwQQCD5s9aQSV0igYZ4XLBNic";
	static String accountId = "60f82992f026ab00702fc98c";
	
	
	public static void main(String []args) throws URISyntaxException, JSONException, IllegalStateException, IOException	{
//		
//		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, accountId).build();
//		JwtGenerator jwtGenerator = client.getJwtGenerator();
//		
//		// API to which the JWT token has to be generated
//		String createCycleUri = zephyrBaseUrl + "/public/rest/api/1.0/cycle";
//		
//		URI uri = new URI(createCycleUri);
//		int expirationInSec = 360;
//		String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
//		
//		// Print the URL and JWT token to be used for making the REST call
//		System.out.println("FINAL API : " +uri.toString());
//		System.out.println("JWT Token : " +jwt);
//		
//		
//		
//		final String getCyclesUri = API_GET_CYCLES.replace("{SERVER}", zephyrBaseUrl) + "projectId=" + projectId
//				+ "&versionId=" + versionId;
//
//		Map<String, String> cycles = getCyclesByProjectVersion(getCyclesUri, client, accessKey);
//		
//		System.out.println(cycles);
//		
//		String pattern = "yyyy-MM-dd";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//		String date = simpleDateFormat.format(new Date());
//		System.out.println(date);
//		
//		/** Cycle Object created - DO NOT EDIT **/
//		JSONObject createCycleObj = new JSONObject();
//		createCycleObj.put("name", cycleName);        			
//		createCycleObj.put("description", cycleDescription);			
//		createCycleObj.put("startDate", date);
//		createCycleObj.put("projectId", projectId);
//		createCycleObj.put("versionId", versionId);
//
//		StringEntity cycleJSON = null;
//		try {
//			cycleJSON = new StringEntity(createCycleObj.toString());
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		cycleId = createCycle(createCycleUri, client, accessKey, cycleJSON);
//		System.out.println("Cycle Created with Cycle Id :" + cycleId);
//
//		/**
//		 * Add tests to Cycle IssueId's
//		 * 
//		 */
//		
//		String addTestsUri = zephyrBaseUrl + "/public/rest/api/1.0/executions/add/cycle/" + cycleId;
//		String[] issueIds = { "RPMXCON-51540", "RPMXCON-51858"}; //Issue Id's to be added to Test Cycle, add more issueKeys separated by comma
//
//		JSONObject addTestsObj = new JSONObject();
//		addTestsObj.put("issues", issueIds);
//		addTestsObj.put("method", "1");
//		addTestsObj.put("projectId", projectId);
//		addTestsObj.put("versionId", versionId);
//
//		StringEntity addTestsJSON = null;
//		try {
//			addTestsJSON = new StringEntity(addTestsObj.toString());
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		String ID = addTestsToCycle(addTestsUri, client, accessKey, addTestsJSON);
//		System.out.println("Tests added successfully  ");
//		
//		
//		Map<String, String> executionIds = new HashMap<String, String>();
//		final String getExecutionsUri = API_GET_EXECUTIONS.replace("{SERVER}", zephyrBaseUrl) + cycleId + "?projectId="
//				+ projectId + "&versionId=" + versionId;
//
//		executionIds = getExecutionsByCycleId(getExecutionsUri, client, accessKey);
//		System.out.println(executionIds);
//		
//		JSONObject statusObj = new JSONObject();
//		statusObj.put("id", "1");
//
//		JSONObject executeTestsObj = new JSONObject();
//		executeTestsObj.put("status", statusObj);
//		executeTestsObj.put("cycleId", cycleId);
//		executeTestsObj.put("projectId", projectId);
//		executeTestsObj.put("versionId", versionId);
//		executeTestsObj.put("comment", "Executed by ZAPI Cloud");
//		
//		for(String issue : executionIds.keySet())
//		{
//			String execId = executionIds.get(issue).split(";")[1];
//			final String updateExecutionUri = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + execId;
//			// System.out.println(updateExecutionUri);
//			// System.out.println(executionIds.get(key));
//			executeTestsObj.put("issueId", executionIds.get(issue).split(";")[0]);
//			// System.out.println(executeTestsObj.toString());
//			StringEntity executeTestsJSON = null;
//			try {
//				executeTestsJSON = new StringEntity(executeTestsObj.toString());
//				} catch (UnsupportedEncodingException e1) {
//				e1.printStackTrace();
//			}
//			updateExecutions(updateExecutionUri, client, accessKey, executeTestsJSON);
//		}
//
////		for (String key : executionIds.keySet()) {
////			final String updateExecutionUri = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + key;
////			// System.out.println(updateExecutionUri);
////			// System.out.println(executionIds.get(key));
////			executeTestsObj.put("issueId", executionIds.get(key).split(";")[0]);
////			// System.out.println(executeTestsObj.toString());
////			StringEntity executeTestsJSON = null;
////			try {
////				executeTestsJSON = new StringEntity(executeTestsObj.toString());
////				} catch (UnsupportedEncodingException e1) {
////				e1.printStackTrace();
////			}
////			updateExecutions(updateExecutionUri, client, accessKey, executeTestsJSON);
////		}
//
//		
	}
	
	public static Map<String, String> getExecutionsByCycleId(String uriStr, ZFJCloudRestClient client,
			String accessKey, int TCcount, int offset) throws URISyntaxException, JSONException {
		Map<String, String> executionIds = new HashMap<String, String>();
		uriStr = uriStr+ "&offset=" + offset; 	
		
		URI uri = new URI(uriStr);
		String jwt = getJWT("GET", uriStr,client);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Authorization", jwt);
		httpGet.setHeader("zapiAccessKey", accessKey);

		try {
			response = restClient.execute(httpGet);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println("Get status line code" + statusCode);

		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity1 = response.getEntity();
			String string1 = null;
			try {
				string1 = EntityUtils.toString(entity1);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println(string1);
			JSONObject allIssues = new JSONObject(string1);
			JSONArray IssuesArray = allIssues.getJSONArray("searchObjectList");
			// System.out.println(IssuesArray.length());
			if (IssuesArray.length() == 0) {
				return executionIds;
			}
			for (int j = 0; j <= IssuesArray.length() - 1; j++) {
				JSONObject jobj = IssuesArray.getJSONObject(j);
				JSONObject jobj2 = jobj.getJSONObject("execution");
				String issueKey = jobj.getString("issueKey");
				String executionId = jobj2.getString("id");
				long IssueId = jobj2.getLong("issueId");
				System.out.println(issueKey+" - "+String.valueOf(IssueId)+";"+executionId);
				if(!executionIds.containsKey(issueKey)) {
				executionIds.put(issueKey, String.valueOf(IssueId)+";"+executionId);
				}
//				executionIds.put(issueKey, executionId);
			}
		
		}
		return executionIds;
	}
	
	
	public static String getCurrentExecution(String uriStr, ZFJCloudRestClient client,
            String accessKey) throws URISyntaxException, JSONException {
        String statusname = "";
        URI uri = new URI(uriStr);
        String jwt = getJWT("GET", uriStr,client);
 
        HttpResponse response = null;
        HttpClient restClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", jwt);
        httpGet.setHeader("zapiAccessKey", accessKey);
 
        try {
            response = restClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
 
        int statusCode = response.getStatusLine().getStatusCode();
        // System.out.println(statusCode);
 
        if (statusCode >= 200 && statusCode < 300) {
            HttpEntity entity1 = response.getEntity();
            String string1 = null;
            try {
                string1 = EntityUtils.toString(entity1);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject execution = new JSONObject(string1);
            JSONObject subexecution = execution.getJSONObject("execution");
            JSONObject subexecution2 = subexecution.getJSONObject("execution");
            JSONObject status = subexecution2.getJSONObject("status");
            statusname = status.getString("name");
            System.out.println(statusname);
 
        }
        return statusname;
    }
	public static String getJWT(String type, String getCyclesUri, ZFJCloudRestClient client) throws URISyntaxException
	{
		String JWT = "";
		URI uri = new URI(getCyclesUri);
		int expirationInSec = 360;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		JWT = jwtGenerator.generateJWT(type, uri, expirationInSec);
		// System.out.println(uri.toString());
				// System.out.println(jwt);
		return JWT;
	}
	
	public static Map<String, String> getCyclesByProjectVersion(String getCyclesUri, ZFJCloudRestClient client,
			String accessKey) throws URISyntaxException, JSONException {
		// TODO Auto-generated method stub

		Map<String, String> cycleMap = new HashMap<String, String>();
		
		String jwt = getJWT("GET", getCyclesUri,client);
		
		URI uri = new URI(getCyclesUri);
		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Authorization", jwt);
		httpGet.setHeader("zapiAccessKey", accessKey);

		try {
			response = restClient.execute(httpGet);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
		// System.out.println(statusCode);

		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity1 = response.getEntity();
			String string1 = null;
			try {
				string1 = EntityUtils.toString(entity1);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println(string1);
			JSONArray cyclesArray = new JSONArray(string1);
			for (int i = 0; i < cyclesArray.length(); i++) {
				JSONObject cycleObj = cyclesArray.getJSONObject(i);
				String cycleID = cycleObj.getString("id");
				String cycleName = cycleObj.getString("name");
				cycleMap.put(cycleID, cycleName);
//				System.out.println(IssuesArray.length());
			}

		}
		return cycleMap;
	}
	
	public static String createCycle(String uriStr, ZFJCloudRestClient client, String accessKey, StringEntity cycleJSON)
			throws URISyntaxException, JSONException {

		URI uri = new URI(uriStr);
		String jwt = getJWT("POST", uriStr,client);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPost createCycleReq = new HttpPost(uri);
		createCycleReq.addHeader("Content-Type", "application/json");
		createCycleReq.addHeader("Authorization", jwt);
		createCycleReq.addHeader("zapiAccessKey", accessKey);
		createCycleReq.setEntity(cycleJSON);

		try {
			response = restClient.execute(createCycleReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		String cycleId = "-1";
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();
			String string = null;
			try {
				string = EntityUtils.toString(entity);
				JSONObject cycleObj = new JSONObject(string);
				cycleId = cycleObj.getString("id");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
		return cycleId;
	}

	public static String addTestsToCycle(String uriStr, ZFJCloudRestClient client, String accessKey, StringEntity addTestsJSON)
			throws URISyntaxException, JSONException, IllegalStateException, IOException {

		URI uri = new URI(uriStr);
		String jwt = getJWT("POST", uriStr,client);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPost addTestsReq = new HttpPost(uri);
		addTestsReq.addHeader("Content-Type", "application/json");
		addTestsReq.addHeader("Authorization", jwt);
		addTestsReq.addHeader("zapiAccessKey", accessKey);
		addTestsReq.setEntity(addTestsJSON);

		try {
			response = restClient.execute(addTestsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
		String string = null;
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();			
			try {
				string = EntityUtils.toString(entity);
				//System.out.println(string);
				JSONObject cycleObj = new JSONObject(entity);
				//System.out.println(cycleObj.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
		return string;
	}
	
	public static String updateExecutions(String uriStr, ZFJCloudRestClient client, String accessKey,
			StringEntity executionJSON) throws URISyntaxException, JSONException, ParseException, IOException {

		URI uri = new URI(uriStr);
		// System.out.println(uri.toString());
		// System.out.println(jwt);
		String jwt = getJWT("PUT", uriStr,client);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPut executeTest = new HttpPut(uri);
		executeTest.addHeader("Content-Type", "application/json");
		executeTest.addHeader("Authorization", jwt);
		executeTest.addHeader("zapiAccessKey", accessKey);
		executeTest.setEntity(executionJSON);

		try {
			response = restClient.execute(executeTest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
		// System.out.println(statusCode);
		String executionStatus = "No Test Executed";
		// System.out.println(response.toString());
		HttpEntity entity = response.getEntity();

		if (statusCode >= 200 && statusCode < 300) {
			String string = null;
			try {
				string = EntityUtils.toString(entity);
				JSONObject executionResponseObj = new JSONObject(string);
				JSONObject descriptionResponseObj = executionResponseObj.getJSONObject("execution");
				JSONObject statusResponseObj = descriptionResponseObj.getJSONObject("status");
				executionStatus = statusResponseObj.getString("description");
				System.out.println(executionResponseObj.get("issueKey") + "--" + executionStatus);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			try {
				String string = null;
				string = EntityUtils.toString(entity);
				JSONObject executionResponseObj = new JSONObject(string);
				cycleId = executionResponseObj.getString("clientMessage");
				// System.out.println(executionResponseObj.toString());
				throw new ClientProtocolException("Unexpected response status: " + statusCode);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
		return executionStatus;
	}
	

}

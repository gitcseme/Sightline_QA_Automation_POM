package pageFactory;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import SightlineObjects.SLProcessingStates;
import automationLibrary.Driver;
import automationLibrary.Element;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ICE_DatasetProgressStatusPage {

	Driver driver;
	String datasetName;
	Boolean isDupeDataset;
	
	public Element getUplaodedFileListCSVlink() {return driver.FindElementById("downloadUploadedDatasetFiles");}
	public Element getProjectLevelProcessingMonitor() {return driver.FindElementById("canvasTaskProcessingWorkload");}
	public Element getEntireProjectProcessingSumamry() {return driver.FindElementByClassName("table");}
	public Element getProgressStatus() {return driver.FindElementById("dvProgress").FindElementByclassName("progressBarText");}
	public Element getprocessingEngineWorkLoadCanvas() {return driver.FindElementById("canvasTaskProcessingWorkload");}
	public Element getEntireProcessingSumamry() {return driver.FindElementById("divProjectLevelProcessingMonitor");}
	public String getProcessingStatus() {return driver.FindElementByCssSelector("#dvProgress").GetAttribute("data-iceprocessingstate");}
	public Element getDupeErrMessageElement() {return driver.FindElementByCssSelector("#dvDatasetProcessingWarnMsg");}
	public String getDupeErrMessage() {return driver.FindElementByXPath("//*[@id='dvDatasetProcessingWarnMsg']").getText();}
	public Element getSLProcessStatus() {return driver.FindElementByCssSelector("#divDatasetProcessingStatus > div:nth-child(2) > div > div > div");}
	public ICE_DatasetProgressStatusPage(Driver driver, String datasetname, boolean isdupedataset)
	{
		this.driver = driver;
		this.datasetName = datasetname;
		this.isDupeDataset = isdupedataset;
	}
	
	
	
	public Boolean isWorkLoadEntireProjectDisplayed()
	{
		
		 Callable<Boolean> cb = new Callable<Boolean>() {public Boolean call()
		 {
			 try{
			 if(driver.FindElementByCssSelector("#canvasTaskProcessingWorkload").Visible())
			 {
			 return true;
			 }
			 return false;
			 }catch(Exception e)
			 {
				 return false;
			 }
			 
			 
		 }};
		 
		   long startingTime = System.currentTimeMillis();
	       long timeout = 20000;
	       //long interval = 500;
	       boolean running = true;
	       Thread thread = Thread.currentThread();
	       while (running)
	       {
	    	   ExecutorService executor = Executors.newFixedThreadPool(1);
	           try
	           {
	        	   Future<Boolean> future = executor.submit(cb);
	               if (future.get())
	               {
	            	   return true;
	               }
	           }
	           catch(Exception e)
	           {
	        	   //e.printStackTrace();
	           }
	        
	           if ((System.currentTimeMillis() - startingTime) > timeout)
	           {
	               running = false;
	           }
	           else
	           {
	              try {
						thread.join(Input.interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	           }
	           executor.shutdown();
	       }
		return false;
	}	
	
	public String getEntireProjectSummaryTableHeader()
	{
		String value = null;
		 Callable<String> cb = new Callable<String>() {public String call()
		 {
			 String text = null;
			 try{
			 text = driver.FindElementByCssSelector("table.table > thead > tr").getText();
			 return text;
			 }catch(Exception e)
			 {
				 return text;
			 }
			 
			 
		 }};
		 
		   long startingTime = System.currentTimeMillis();
	       long timeout = 20000;
	       //long interval = 500;
	       boolean running = true;
	       Thread thread = Thread.currentThread();
	       while (running)
	       {
	    	   ExecutorService executor = Executors.newFixedThreadPool(1);
	           try
	           {
	        	   Future<String> future = executor.submit(cb);
	        	   value = future.get();
	               if (value != null)
	               {
	            	   return value;
	               }
	           }
	           catch(Exception e)
	           {
	        	   //e.printStackTrace();
	           }
	        
	           if ((System.currentTimeMillis() - startingTime) > timeout)
	           {
	               running = false;
	           }
	           else
	           {
	              try {
						thread.join(Input.interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	           }
	           executor.shutdown();
	       }
		return value;
	}	
	

	public String getEntireProjectSummaryTableValues()
	{
		String value = null;
		 Callable<String> cb = new Callable<String>() {public String call()
		 {
			 String text = null;
			 try{
			 text = driver.FindElementByCssSelector("table.table > tbody").getText();
			 return text;
			 }catch(Exception e)
			 {
				 return text;
			 }
			 
			 
		 }};
		 
		   long startingTime = System.currentTimeMillis();
	       long timeout = 20000;
	       //long interval = 500;
	       boolean running = true;
	       Thread thread = Thread.currentThread();
	       while (running)
	       {
	    	   ExecutorService executor = Executors.newFixedThreadPool(1);
	           try
	           {
	        	   Future<String> future = executor.submit(cb);
	        	   value = future.get();
	               if (value != null)
	               {
	            	   return value;
	               }
	           }
	           catch(Exception e)
	           {
	        	   //e.printStackTrace();
	           }
	        
	           if ((System.currentTimeMillis() - startingTime) > timeout)
	           {
	               running = false;
	           }
	           else
	           {
	              try {
						thread.join(Input.interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	           }
	           executor.shutdown();
	       }
		return value;
	}
	
	public String getSLProcessingStage(int totalTimeToExecute, int interval)
	{
		String value = null;
		 Callable<String> cb = new Callable<String>() {public String call()
		 {
			 String text = null;
			 try{
			 text = driver.FindElementByCssSelector("#divDatasetProcessingStatus > div:nth-child(2) > div > div > div > div").getText();
			 return text;
			 }catch(Exception e)
			 {
				 return text;
			 }
			 
			 
		 }};
		 
		   long startingTime = System.currentTimeMillis();
	       int timeout = totalTimeToExecute;
	       int interva = interval;
	       boolean running = true;
	       Thread thread = Thread.currentThread();
	       while (running)
	       {
	    	   ExecutorService executor = Executors.newFixedThreadPool(1);
	           try
	           {
	        	   Future<String> future = executor.submit(cb);
	        	   value = future.get();
	               if (value != null)
	               {
	            	   return value;
	               }
	           }
	           catch(Exception e)
	           {
	        	   //e.printStackTrace();
	           }
	        
	           if ((System.currentTimeMillis() - startingTime) > timeout)
	           {
	               running = false;
	           }
	           else
	           {
	              try {
						thread.join(interva);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	           }
	           executor.shutdown();
	       }
		return value;
	}	

	public SLProcessingStates getSLProcessingStatgesCompleted(int timeout, int interval) throws InterruptedException
	{
		boolean isDupeErrorTested = false;
		long startingTime = System.currentTimeMillis();
		SLProcessingStates ps = new SLProcessingStates();
		
		while(!ps.isPublished())
		{
			if ((System.currentTimeMillis() - startingTime) > timeout)
	           {
	               return ps;
	           }
			String status = getSLProcessingStage(10000,500).toLowerCase();
		 switch(status)
		 {
		 	case "processing":
		 	{
		 		ps.setProcessing(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "post - processing":
		 	{
		 		ps.setPostProcessing(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "post - processing complete":
		 	{
		 		ps.setPostProcessingComplete(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "enriching data":
		 	{
		 		ps.setEnrichingData(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "enriched data":
		 	{
		 		ps.setEnrichedData(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "indexing":
		 	{
		 	ps.setIndexing(true);	
		 	Thread.sleep(interval);
		 	break;
		 	}
		 	case "indexed":
		 	{
		 		ps.setIndexed(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "analytics running":
		 	{
		 		if(isDupeDataset && !isDupeErrorTested)
				{
		 			isDupeErrorTested = true;
					//Test Case No: 10805, Priority 1
					Assert.assertTrue(getDupeErrMessage().contains("Note: Zero processed records survived DeDuplication. No records ingested into Sightline."));
				}
		 		ps.setAnalyticsRunning(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "analytics complete":
		 	{
		 		ps.setAnalyticsComplete(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	case "published":
		 	{
		 		ps.setPublished(true);
		 		Thread.sleep(interval);
		 		break;
		 	}
		 	default:
		 	{
		 		driver.waitForPageToBeReady();
		 		if(driver.FindElementByCssSelector("h1.page-title").getText().contains("Dataset Summary:"))
		 		{
		 			ps.setPublished(true);
		 			break;
		 		}
		 		return ps;
		 	}
		 }
		 
		}
		return ps;
	}	
}

package testScriptsSmoke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.testng.annotations.BeforeSuite;

import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;
import configsAndTestData.Environment;
import configsAndTestData.TestData;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class Input {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	//Config and test data files---------------------------------//
	public static ConfigMain config;
	public static Environment envConfig;
	
	public static TestData testData;
	//ConfigMain data---------------------------------------------
	public  static String newProject;
	public static String ingestion;
	public static  String suite;
	public static String browserName;
	public static  String screenShotOnPass;
	public static  String screenShotOnFail;
	public static int wait3;
	public static int wait30;
	public static int wait60;
	public static int wait90;
	public static int wait120;
	public static int interval;
	public static String batchFilesPath;
	public static int numberOfDataSets;
	public static boolean extentReportMethodWise;
	public static boolean logJiraTicket;
	public static String jiraUrl;
	public static String jiraUserName;
	public static String jiraToken;
	public static String jiraProject;
	public static String testingBuild;
	public static boolean headlessMode;
	
			
	//Environment data---------------------------------------------
	public static String url;
	public static String projectName;
	public static String sa1password;
	public static String sa1userName;
	public static String da1password;
	public static String da1userName;
	public static String pa1userName;
	public static String pa1password;
	public static String rmu1userName;
	public static String rmu1password;
	public static String rev1userName;
	public static String rev1password;
	public static String shareTo;
	public static String pa1FullName;
	public static String rmu1FullName;
	public static String rev1FullName;
	public static String pa2FullName;
	public static String rmu2FullName;
	public static String rev2FullName;
	public static String pa2userName;
	public static String pa2password;
	public static String rmu2userName;
	public static String rmu2password;
	public static String rev2userName;
	public static String rev2password;
	public static String domainName;
	public static String ICEProjectName;
	//Test data------------------------------------------------------
	public static String searchString1;
	public static String searchString2;
	public static int pureHitSeachString1;
	public static int pureHitSeachString2;
	public static int searchString1ANDsearchString2;
	public static int searchString1ORsearchString2;
	public static int searchString1NOTsearchString2;
	public static int searchString2NOTsearchString1;
	public static String audioSearchString1;
	public static int audioSearchString1pureHit;
	public static String  conceptualSearchString1;
	public static int  conceptualSearchString1PureHit;
	public static String  metaDataCN;
	public static int  metaDataCNcount;
	public static String  Collection1KFolder;
	public static String  FamilyFolder;
	public static String  AllSourcesFolder;
	public static String  Collection1KDATFile;
	public static String  FamilyDATFile;
	public static String  AllSourcesDATFile;
	public static String  Collection1KDockey;
	public static String  FamilyDockey;
	public static String  AllSourcesDockey;
	public static String  Collection1KSourceDatField2;
	public static String  FamilySourceDatField2;
	public static String  AllSourcesSourceDatField2;
	public static String Collection1KTextFile;
	public static String FamilyTextFile;
	public static String AllSourcesTextFile;
	public static String FamilyNativeFile;
	public static String AllSourcesNativeFile;
	public static String SourceLocation;
	public static int totalNumberOfDocs;
	public static int totalNumberOfDocsincludeadvoption;
	public static String MasterPDF1location;
	public static String MasterPDF2location;
	public static String iCESmokeFolderPath;
	public static String prodPath;
	public static String SourceDatFieldCustom;
	public static String CustomFieldname;


	@BeforeSuite(alwaysRun=true)
	public void loadEnvConfig() throws ParseException, InterruptedException, IOException {
	
		System.out.println("*****************************************************");
		UtilityLog.info("*****************************************************");
		//Common Data-------------------------------------------------------------
		config = (ConfigMain) new ConfigLoader().load("ConfigMain");
		//Reading from Config Main File
		envConfig = (Environment) new ConfigLoader().load(config.getEnv());
		//Reading from Jenkins
		//envConfig = (Environment) new ConfigLoader().load(System.getProperty("tEnvironment"));
		System.out.println("Running scripts on "+config.getEnv()+" Environment");
		UtilityLog.info("Running scripts on "+config.getEnv()+" Environment");
		
		newProject = config.getNewProject();
		ingestion = config.getIngestion();
		suite = config.getSuite();
		numberOfDataSets = config.getNumberOfDataSets();
		browserName = config.getBrowserName();
		screenShotOnPass = config.getScreenShotOnPass();
		screenShotOnFail = config.getScreenShotOnFail();
		batchFilesPath= config.getBatchFilesPath();
		wait3 = config.getWait3();
		wait30 = config.getWait30();
		wait60 = config.getWait60();
		wait90 = config.getWait90();
		wait120 = config.getWait120();
		interval = config.getInterval();
		extentReportMethodWise = config.isExtentReportMethodWise();
		logJiraTicket = config.isLogJiraTicket();
		jiraUrl = config.getJiraUrl();
		jiraUserName = config.getJiraUserName();
		jiraToken = config.getJiraToken();
		jiraProject = config.getJiraProject();
		testingBuild = config.getTestingBuild();
		iCESmokeFolderPath = config.getICESmokeFolderPath();
		headlessMode = config.isHeadlessMode();
		
		/*
		 * Ingestion Data
		 */
		Collection1KFolder = config.getCollection1KFolder();
		FamilyFolder = config.getFamilyFolder();
		AllSourcesFolder = config.getAllSourcesFolder();
		Collection1KDATFile =config.getCollection1KDATFile();
		FamilyDATFile = config.getFamilyDATFile();
		AllSourcesDATFile = config.getAllSourcesDATFile();
		Collection1KDockey = config.getCollection1KDockey();
		FamilyDockey = config.getFamilyDockey();
		AllSourcesDockey =  config.getAllSourcesDockey();
		Collection1KSourceDatField2 = config.getCollection1KSourceDatField2();
		FamilySourceDatField2 = config.getFamilySourceDatField2();
		AllSourcesSourceDatField2 = config.getAllSourcesSourceDatField2();
		Collection1KTextFile = config.getCollection1KTextFile();
		FamilyTextFile = config.getFamilyTextFile();
		AllSourcesTextFile = config.getAllSourcesTextFile();
		FamilyNativeFile = config.getFamilyNativeFile();
		AllSourcesNativeFile = config.getAllSourcesNativeFile();
		SourceDatFieldCustom =config.getSourceDatFieldCustom();
		
		//Environment data-------------------------------------------------------------
		url=envConfig.getUrl();
		projectName= envConfig.getProjectName();
		domainName= envConfig.getDomainName();
		sa1userName=envConfig.getSa1userName();
		sa1password = envConfig.getSa1password();
		pa1userName = envConfig.getPa1userName();
		pa1password=envConfig.getPa1password();
		rmu1userName =envConfig.getRmu1userName();
		rmu1password =envConfig.getRmu1password();
		rev1userName =envConfig.getRev1userName();
		rev1password =envConfig.getRev1password();
		pa2userName = envConfig.getPa2userName();
		pa2password=envConfig.getPa2password();
		rmu2userName =envConfig.getRmu2userName();
		rmu2password =envConfig.getRmu2password();
		rev2userName =envConfig.getRev2userName();
		rev2password =envConfig.getRev2password();
		shareTo = envConfig.shareTo;
		pa1FullName= envConfig.getPa1FullName();
		rmu1FullName= envConfig.getRmu1FullName();
		rev1FullName= envConfig.getRev1FullName();
		pa2FullName= envConfig.getPa2FullName();
		rmu2FullName= envConfig.getRmu2FullName();
		rev2FullName= envConfig.getRev2FullName();
		prodPath= envConfig.getProdpath();
		SourceLocation = envConfig.getSourceLocation();
		ICEProjectName = envConfig.getICEProjectName();

		//Test data-------------------------------------------------------------
		
		loadSuiteTestData();//Load required suite data first, smoke or regression one
		testData = (TestData) new ConfigLoader().load("TestData");
		searchString1 = testData.getSearchString1();
		searchString2 = testData.getSearchString2();
		pureHitSeachString1 = testData.getPureHitSeachString1();
		pureHitSeachString2 = testData.getPureHitSeachString2();
		searchString1ANDsearchString2 = testData.getSearchString1ANDsearchString2();
		searchString1ORsearchString2 = testData.getSearchString1ORsearchString2();
		searchString1NOTsearchString2 = testData.getSearchString1NOTsearchString2();
		searchString2NOTsearchString1 = testData.getSearchString2NOTsearchString1();
		audioSearchString1 = testData.getAudioSearchString1();
		audioSearchString1pureHit = testData.getAudioSearchString1pureHit();
		conceptualSearchString1 = testData.getSearchString1();
		conceptualSearchString1PureHit = testData.getConceptualSearchString1PureHit();
		metaDataCN=testData.getMetaDataCN();
		metaDataCNcount = testData.getMetaDataCNcount();
		totalNumberOfDocs = testData.getTotalNumberOfDocs();
		totalNumberOfDocsincludeadvoption = testData.getTotalNumberOfDocsincludeadvoption();
		MasterPDF1location = testData.getMasterPDF1location();
		MasterPDF2location = testData.getMasterPDF2location();
		CustomFieldname = testData.CustomFieldname;
		
		System.out.println("*****************************************************");
		UtilityLog.info("*****************************************************");
		
		//createproject if configured
		projectCreationAndUserAssignment();
		
		//do ingestion if configured 
		ingestion();
		
		//BulkRelase all docs
		//releaseDocs();
		
	}
	
	
	public void projectCreationAndUserAssignment() throws ParseException, InterruptedException, IOException {
		
		
		if(newProject.equalsIgnoreCase("yes")){
				driver = new Driver();
				
				System.out.println("******Creating new project********");
				UtilityLog.info("******Creating new project********");
				//clean browsers---------------------------------------------------------
				System.out.println("Clearing browser cache, Please wait.......");
				UtilityLog.info("Clearing browser cache, Please wait.......");
				LoginPage.clearBrowserCache();
					
				
				loginPage = new LoginPage(driver);
				
			  
			    loginPage.loginToSightLine(sa1userName, sa1password);
				String hcode = "HC"+Utility.dynamicNameAppender();
				ProjectPage page = new ProjectPage(driver);
				page.AddNonDomainProject(projectName, hcode);
			
			 	ManageUsersPage userpage = new ManageUsersPage(driver);
			 	userpage.AddUserstoProject(projectName);
				
			 	userpage.BulkUserAccess(Input.projectName);
			 /*	System.out.println("add rights manually");
				Thread.sleep(40000);*/
			 	
				loginPage.logout();
				
				System.out.println("******Project creation and user assignment is done********\n");
				UtilityLog.info("******Project creation and user assignment is done********\n");
				
		}else {
			System.out.println("Running on exsiting project : "+Input.projectName);
			UtilityLog.info("Running on exsiting project : "+Input.projectName);
		}
	}

	public void ingestion() throws InterruptedException{
		try{
		if(ingestion.equalsIgnoreCase("yes")){
				driver = new Driver();
				loginPage = new LoginPage(driver);
				ArrayList<String> dataset = new ArrayList<String>();
				if(Input.suite.equalsIgnoreCase("Regression")){
					dataset.add("Automation_Collection1K_Tally");
//					dataset.add("Automation_AllSources");
					//dataset.add("Automation_20Family_20Threaded");
				}else if(Input.suite.equalsIgnoreCase("smoke")){
					dataset.add("Automation_AllSources");
				}
				System.out.println(dataset);
				UtilityLog.info(dataset);
				System.out.println("******Ingestion will start for "+dataset+"********");
				UtilityLog.info("******Ingestion will start for "+dataset+"********");
				loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
				for (int i = 0; i < dataset.size(); i++) {
					baseClass = new BaseClass(driver);
					baseClass.selectproject();
					
				    IngestionPage page1 = new IngestionPage(driver);
			     	if(!page1.AddOnlyNewIngestion(dataset.get(i).toString())){
			     		System.out.println("Execution aborted!, Ingestion did not go well! take a look and continue.");
						UtilityLog.info("Execution aborted!, Ingestion did not go well! take a look and continue. ");
						System.exit(1);
						
			     	}
			     		
		     	
			 	    SessionSearch search = new SessionSearch(driver);
			 	    
			     	int count = search.basicMetaDataSearch("IngestionName", null, page1.IngestionName, null);
			     	if(dataset.get(i).toString().equals("Automation_Collection1K_Tally")){
			     		if(count!=1003)
			     		{
			     			System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
			     			System.out.println(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
			     			System.exit(1);
			     		}
			     		else 
			     			UtilityLog.info(dataset+" has all the documnets");
			     	}
			     	else if(dataset.get(i).toString().equals("Automation_20Family_20Threaded")){
			     		if(count!=74)
			     		{
			     			System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
			     			System.out.println(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
			     			System.exit(1);
			     		}
			     		else 
			     			UtilityLog.info(dataset+" has all the documnets");
			     	}
			     	else if(dataset.get(i).toString().equals("Automation_AllSources")){
			     		if(count!=125)
			     		{
			     			System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
			     			System.out.println(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()+" : Docs number mismatch! take a look and then continue the execution!!");
			     			System.exit(1);
			     		}
			     		else 
			     			UtilityLog.info(dataset+" has all the documnets");
			     	}
			     	if(!search.bulkReleaseIngestions("Default Security Group")){
			     		 System.out.println("Execution aborted!");
			 			UtilityLog.info("Execution aborted!");
			 			System.out.println("Bulk relese did not go well! take a look and continue!!");
			 			UtilityLog.info("Bulk relese did not go well! take a look and continue!!");
			 			System.exit(1);
			     	}
			     	loginPage.logout();
				}
				System.out.println("******Ingestion Completed********");
				UtilityLog.info("******Ingestion Completed********");
		}else{
			System.out.println("Choosen not to perform any ingestion");
			UtilityLog.info("Choosen not to perform any ingestion");
		}
		}catch (Exception e) {
			System.out.println("Execution aborted!, Something went wrong during ingestion.");
			UtilityLog.info("Execution aborted!, Something went wrong during ingestion.");
			System.exit(1);
		}
		
}
//Below function loads test data according to the selection made in main config	
public void loadSuiteTestData() throws IOException {
	FileInputStream Fread = null;
	if(Input.suite.equalsIgnoreCase("smoke") && numberOfDataSets == 1)
	   Fread =new FileInputStream(System.getProperty("user.dir")+"/src/main/java/configsAndTestData/"+"TestData_Smoke.xml"); 
	else if(Input.suite.equalsIgnoreCase("smoke") && numberOfDataSets == 3)	
	   Fread =new FileInputStream(System.getProperty("user.dir")+"/src/main/java/configsAndTestData/"+"TestData_Regression.xml"); 
	else if(Input.suite.equalsIgnoreCase("Regression") && numberOfDataSets==3)
		   Fread =new FileInputStream(System.getProperty("user.dir")+"/src/main/java/configsAndTestData/"+"TestData_Regression.xml"); 
        
	FileOutputStream Fwrite=new FileOutputStream(System.getProperty("user.dir")+"/src/main/java/configsAndTestData/"+"TestData.xml") ; 
    int c; 
    while((c=Fread.read())!=-1) 
      Fwrite.write((char)c); 
      Fread.close(); 
      Fwrite.close(); 
	
   System.out.println("Test data is copied to TestData.xml");
   UtilityLog.info("Test data is copied to TestData.xml");
}

//to make sure all docs released to default SG -helpful when ingestion done manually!
public void releaseDocs() throws InterruptedException {
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
	  SessionSearch search = new SessionSearch(driver);
	  search.basicContentSearch("*");
	  search.bulkRelease("Default Security Group");  
	  System.out.println("Docs released to default security group!");
	  UtilityLog.info("Docs released to default security group!");
	  loginPage.logout();
}
	
}

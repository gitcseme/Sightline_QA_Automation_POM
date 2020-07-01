package testScriptsSmoke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;


import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;
import configsAndTestData.Environment;
import configsAndTestData.TestData;
import pageFactory.BaseClass;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class Input {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
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
	public static int wait30;
	public static int wait60;
	public static int wait90;
	public static int wait120;
	public static int interval;
	public static String batchFilesPath;
	public static int numberOfDataSets;
	
	//Environment data---------------------------------------------
	public static String url;
	public static String prodPath;
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
	

	@BeforeSuite(alwaysRun=true)
	public void loadEnvConfig() throws ParseException, InterruptedException, IOException {
	
		System.out.println("*****************************************************");
		//Common Data-------------------------------------------------------------
		config = (ConfigMain) new ConfigLoader().load("ConfigMain");
		envConfig = (Environment) new ConfigLoader().load(config.env);
		System.out.println("Running scripts on "+config.env+" Environment");
		
		newProject = config.newProject;
		ingestion = config.ingestion;
		suite = config.suite;
		numberOfDataSets = config.numberOfDataSets;
		browserName = config.browserName;
		screenShotOnPass = config.screenShotOnPass;
		screenShotOnFail = config.screenShotOnFail;
		batchFilesPath= config.batchFilesPath;
		wait30 = config.wait30;
		wait60 = config.wait60;
		wait90 = config.wait90;
		wait120 = config.wait120;
		interval = config.interval;
		
		Collection1KFolder = config.Collection1KFolder;
		FamilyFolder = config.FamilyFolder;
		AllSourcesFolder = config.AllSourcesFolder;
		Collection1KDATFile =config.Collection1KDATFile;
		FamilyDATFile = config.FamilyDATFile;
		AllSourcesDATFile = config.AllSourcesDATFile;
		Collection1KDockey = config.Collection1KDockey;
		FamilyDockey = config.FamilyDockey;
		AllSourcesDockey =  config.AllSourcesDockey;
		Collection1KSourceDatField2 = config.Collection1KSourceDatField2;
		FamilySourceDatField2 = config.FamilySourceDatField2;
		AllSourcesSourceDatField2 = config.AllSourcesSourceDatField2;
		Collection1KTextFile = config.Collection1KTextFile;
		FamilyTextFile = config.FamilyTextFile;
		AllSourcesTextFile = config.AllSourcesTextFile;
		FamilyNativeFile = config.FamilyNativeFile;
		AllSourcesNativeFile = config.AllSourcesNativeFile;
		
		//Environment data-------------------------------------------------------------
		url=envConfig.url;
		projectName= envConfig.projectName;
		domainName= envConfig.domainName;
		sa1userName=envConfig.sa1userName;
		sa1password = envConfig.sa1password;
		pa1userName = envConfig.pa1userName;
		pa1password=envConfig.pa1password;
		rmu1userName =envConfig.rmu1userName;
		rmu1password =envConfig.rmu1password;
		rev1userName =envConfig.rev1userName;
		rev1password =envConfig.rev1password;
		pa2userName = envConfig.pa2userName;
		pa2password=envConfig.pa2password;
		rmu2userName =envConfig.rmu2userName;
		rmu2password =envConfig.rmu2password;
		rev2userName =envConfig.rev2userName;
		rev2password =envConfig.rev2password;
		shareTo = envConfig.shareTo;
		pa1FullName= envConfig.pa1FullName;
		rmu1FullName= envConfig.rmu1FullName;
		rev1FullName= envConfig.rev1FullName;
		pa2FullName= envConfig.pa2FullName;
		rmu2FullName= envConfig.rmu2FullName;
		rev2FullName= envConfig.rev2FullName;
		prodPath= envConfig.prodPath;
		SourceLocation = envConfig.SourceLocation;
		//Test data-------------------------------------------------------------
		
		loadSuiteTestData();//Load required suite data first, smoke or regression one
		testData = (TestData) new ConfigLoader().load("TestData");
		searchString1 = testData.searchString1;
		searchString2 = testData.searchString2;
		pureHitSeachString1 = testData.pureHitSeachString1;
		pureHitSeachString2 = testData.pureHitSeachString2;
		searchString1ANDsearchString2 = testData.searchString1ANDsearchString2;
		searchString1ORsearchString2 = testData.searchString1ORsearchString2;
		searchString1NOTsearchString2 = testData.searchString1NOTsearchString2;
		searchString2NOTsearchString1 = testData.searchString2NOTsearchString1;
		audioSearchString1 = testData.audioSearchString1;
		audioSearchString1pureHit = testData.audioSearchString1pureHit;
		conceptualSearchString1 = testData.conceptualSearchString1;
		conceptualSearchString1PureHit = testData.conceptualSearchString1PureHit;
		metaDataCN=testData.metaDataCN;
		metaDataCNcount = testData.metaDataCNcount;
		totalNumberOfDocs = testData.totalNumberOfDocs;
		totalNumberOfDocsincludeadvoption = testData.totalNumberOfDocsincludeadvoption;
		MasterPDF1location = testData.MasterPDF1location;
		MasterPDF2location = testData.MasterPDF2location;
		
		System.out.println("*****************************************************");
		
		//createproject if configured
		projectCreationAndUserAssignment();
		
		//do ingestion if configured 
		ingestion();
		
		//BulkRelase all docs
		//releaseDocs();
		
	}
	
	
	public void projectCreationAndUserAssignment() throws ParseException, InterruptedException, IOException {
		
		driver = new Driver();
		if(newProject.equalsIgnoreCase("yes")){
				
				System.out.println("******Creating new project********");
				//clean browsers---------------------------------------------------------
				System.out.println("Clearing browser cache, Please wait.......");
				LoginPage.clearBrowserCache();
					
				
				lp = new LoginPage(driver);
				
			  
			    lp.loginToSightLine(sa1userName, sa1password);
				String hcode = "HC"+Utility.dynamicNameAppender();
				ProjectPage page = new ProjectPage(driver);
				page.AddNonDomainProject(projectName, hcode);
			
			 	ManageUsersPage userpage = new ManageUsersPage(driver);
			 	userpage.AddUserstoProject(projectName);
				
			 	userpage.BulkUserAccess(Input.projectName);
			 	System.out.println("add rights manually");
				Thread.sleep(40000);
			 	
				lp.logout();
				
				System.out.println("******Project creation and user assignment is done********\n");
				
		}else {
			System.out.println("Running on exsiting project : "+Input.projectName);
		}
	}

	public void ingestion() throws InterruptedException{
		
		if(ingestion.equalsIgnoreCase("yes")){
				driver = new Driver();
				lp = new LoginPage(driver);
				ArrayList<String> dataset = new ArrayList<String>();
				if(Input.suite.equalsIgnoreCase("Regression")){
					dataset.add("Automation_Collection1K_Tally");
					dataset.add("Automation_AllSources");
					dataset.add("Automation_20Family_20Threaded");
				}else if(Input.suite.equalsIgnoreCase("smoke")){
					dataset.add("Automation_AllSources");
				}
				System.out.println(dataset);
				System.out.println("******Ingestion will start for "+dataset+"********");
				lp.loginToSightLine(Input.pa1userName,Input.pa1password);
				for (int i = 0; i < dataset.size(); i++) {
					bc = new BaseClass(driver);
					bc.selectproject();
					
				    IngestionPage page1 = new IngestionPage(driver);
			     	page1.AddOnlyNewIngestion(dataset.get(i).toString());
			     	
			 	    SessionSearch search = new SessionSearch(driver);
			 	    
			     	int count = search.basicMetaDataSearch("IngestionName", null, page1.IngestionName, null);
			     	if(dataset.get(i).toString().equals("Automation_Collection1K_Tally"))
			     		Assert.assertEquals(count, 1003);
			     	else if(dataset.get(i).toString().equals("Automation_20Family_20Threaded"))
			     		Assert.assertEquals(count, 74);
			     	else if(dataset.get(i).toString().equals("Automation_AllSources"))
			     		Assert.assertEquals(count, 125);
			     		
			     	search.bulkRelease("Default Security Group");  
			     	lp.logout();
				}
				System.out.println("******Ingestion Completed********");
		}else{
			System.out.println("Choosen not to perform any ingestion");
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
}

//to make sure all docs released to default SG -helpful when ingestion done manually!
public void releaseDocs() {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
	  SessionSearch search = new SessionSearch(driver);
	  search.basicContentSearch("*");
	  search.bulkRelease("Default Security Group");  
	  System.out.println("Docs released to default security group!");
	  lp.logout();
}
	
}

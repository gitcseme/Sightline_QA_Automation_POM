package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;


   public class Production_Regression {
	Driver driver;
	LoginPage lp;
	ProductionPage page;
	TagsAndFoldersPage tp;
	SessionSearch ss;
	BaseClass bc;
	
	String productionname;
	String productionname1= "P1"+Utility.dynamicNameAppender();
	String exportname= "EXP"+Utility.dynamicNameAppender();
	String PrefixID;
	String SuffixID;
	String foldername;
	String templatername = "TempProd"+Utility.dynamicNameAppender(); 
	String Tagname;
	String Tagnameprev = "Privileged";
	String Tagnametech = "Technical_Issue" + Utility.dynamicNameAppender();

	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		foldername = "FolderProd"+Utility.dynamicNameAppender(); 
		Tagname = "Tag"+Utility.dynamicNameAppender();
		
		Input in = new Input();
		in.loadEnvConfig();
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	//Open browser
	
		driver = new Driver();
		page = new ProductionPage(driver);

		//Login as PA
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		 tp = new TagsAndFoldersPage(driver);
		 tp.CreateFolder(foldername,"Default Security Group");
		 ss = new	 SessionSearch(driver); 
		 ss.basicContentSearch("crammer");
		 ss.bulkFolderExisting(foldername);
		 tp.CreateTagwithClassification(Tagname,"Privileged");
		 

		 
		
//		lp.logout();
		
		
	}
	
	@BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		productionname= "P"+Utility.dynamicNameAppender();
		PrefixID = "A_"+Utility.dynamicNameAppender();;
		SuffixID = "_P"+Utility.dynamicNameAppender();
		System.out.println("------------------------------------------");
	    System.out.println("Executing method : " + testMethod.getName());  
//	    lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	    page = new ProductionPage(driver);
	 }

	
	    @Test(groups={"regression"},priority=1)
	    public void AddNewProduction() throws ParseException, InterruptedException, IOException {
	    	bc.stepInfo("Test Case Id : RPMXCON-55716 'To Verify that End-to-end productions functionality is working properly'");
	    System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.CreateProduction(productionname, PrefixID, SuffixID, foldername, Tagnameprev);
	  
	  }
      
	    
	    @Test(groups={"regression"},priority=2)
	   public void Productionwithallredaction() throws Exception {
	    	bc.stepInfo("Test Case Id : RPMXCON-56118 RPMXCON-56119 RPMXCON-56120");
	   System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	   page.Productionwithallredactions(productionname, PrefixID, SuffixID, foldername, Tagname);
	  
	  }

//	    @Test(groups={"regression"},priority=3)
	    public void ExportwithpriorProduction() throws ParseException, InterruptedException, IOException {
	    	
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	    page.ExportwithpriorProduction(exportname, PrefixID, SuffixID, foldername);
   	  }
	   
//	    @Test(groups={"regression"},priority=4)
		 public void ProductionwithNatives() throws Exception {
			 bc.stepInfo("Test Case Id : RPMXCON-56149 'Verify that for Native section should be displayed message"
			 		+ " which will inform the user about the privileged and redacted docs from Production'"); 
		 System.out.println("******Execution started for ProductionwithNatives********");
		 page.ProductionwithNatives(productionname,productionname1, PrefixID, SuffixID, foldername,templatername);
	  }
		 
//	    @Test(groups={"regression"},priority=5)
		 public void ProductionwithTechIssuetags() throws ParseException, InterruptedException, IOException {
			 bc.stepInfo("Test Case Id : RPMXCON-56106 'Verify the error message for TIFF/PDF component when 'Enable redaction without selecting redaction tag''");
		 System.out.println("******Execution started for ProductionwithTechIssuetags********");
		 tp.CreateTagwithClassification(Tagnametech,"Technical Issue");
		 ss.basicContentSearch("crammer");
		 ss.bulkTagExisting(Tagnametech);
		 page.ProductionwithTechIssuetags(productionname, PrefixID, SuffixID, foldername,Tagnameprev,Tagnametech);
	  }
	 	
	   //added by Narendra	
//		@Test(groups={"regression"},priority=6)
	    public void Filter() throws ParseException, InterruptedException, IOException {
	    	
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionFilter();
	  
	  }
	   
//		@Test(groups={"regression"},priority=7)
	    public void Sort() throws ParseException, InterruptedException, IOException {
	    	bc.stepInfo("Test Case Id : RPMXCON-55624 'To Verify ProjectAdmin will be able to sort by Production Name, Status, Locked, UserName,Production Date'");	
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionSort();
	  
	  }
	  
//		@Test(groups={"regression"},priority=8)
	   public void Grid() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-55620 'To Verify Admin will be able to view the production sets and productions in a grid view'");
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.GridView();
	  
	  }
	    
//		@Test(groups={"regression"},priority=9)
	   public void VerifyTemplate() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-55622 'To Verify ProjectAdmin will be able to select a previous production configuration created and save it as a template'");	
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String tempName = "newProd"+Utility.dynamicNameAppender();
		page.SaveTemplate(tempName);
	  
	  }
	   
//		@Test(groups={"regression"},priority=10)
	   public void DeleteTemplate() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-55623 'To Verify ProjectAdmin be able to manage (delete) the custom templates that has been created'");	  
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String tempName = "newProd"+Utility.dynamicNameAppender();
		page.DeleteTemplate(tempName);
	  
	  }
	   
//		@Test(groups={"regression"},priority=11)
	   public void VerifyProductionSet() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-55621 'To Verify ProjectAdmin will be able to create one or more production sets'");
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String prodsetName = "newSet"+Utility.dynamicNameAppender();
		page.CreateProductionSets(prodsetName);
		page.VerifyProductionSet(prodsetName);
		page.DeleteteProductionSets(prodsetName);
	  
	  }
      
//		@Test(groups={"regression"},priority=12)
	   public void LockProduction() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-47726 'To Verify Admin will be able to flag a production as ‘Locked’ only for Production in 'Complete' State'");  
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionLock();
	  
	  }
		
//		@Test(groups={"regression"},priority=13)
	   public void CustomizedTemplate() throws ParseException, InterruptedException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-55625 'To Verify ProjectAdmin while creating "
		   		+ "a production configuration,can select one of the templates, or can customize it and save it as a custom production template'");  
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String tempName = "newtemp"+Utility.dynamicNameAppender();
		page.CustomizeTemplate(tempName,productionname);
	  
	  }
		
//		@Test(groups={"regression"},priority=14)
		   public void ProductionDeleteCheck() throws ParseException, InterruptedException, IOException {
			   bc.stepInfo("Test Case Id : RPMXCON-55658 'To Verify Production with Completed Status has the "
			   		+ "'Delete' option disabled under the Action drop down menu.(in both Tiles/Grid View)'");
			System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
			page.ProductionDeletionCheck();
		  
		  }
	
//		@Test(groups={"regression"},priority=15)
		   public void TiffVerification() throws ParseException, InterruptedException, IOException {
			   
			System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");

			String productionName = "prod"+Utility.dynamicNameAppender();
			page.TiffPDF(productionName);
		  
		  }
		
		
//		@Test(groups={"regression"},priority=16)
		   public void ProductionDelete() throws ParseException, InterruptedException, IOException {
			   bc.stepInfo("Test Case Id : RPMXCON-55652 'To verify Delete option and verify the status of the Draft Production'");
			System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
			page.DeleteProduction();
		  
		  }
	
	 
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 	 }
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
	@AfterClass(alwaysRun = true)
     public void close(){
		lp.logout();
		driver.Quit();
  	}
}

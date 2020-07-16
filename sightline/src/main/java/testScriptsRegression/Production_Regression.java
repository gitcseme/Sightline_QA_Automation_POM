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
	
	String productionname= "P"+Utility.dynamicNameAppender();
	String productionname1= "P1"+Utility.dynamicNameAppender();
	String exportname= "EXP"+Utility.dynamicNameAppender();
	String PrefixID = "A_"+Utility.dynamicNameAppender();;
	String SuffixID = "_P"+Utility.dynamicNameAppender();;
	String foldername = "FolderProd"+Utility.dynamicNameAppender(); 
	String templatername = "TempProd"+Utility.dynamicNameAppender(); 
	String Tagname = "Tag"+Utility.dynamicNameAppender();
	String Tagnameprev = "Privileged";
	String Tagnametech = "Technical_Issue";
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	//Open browser
		
		Input in = new Input(); in.loadEnvConfig();
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		
		 tp = new TagsAndFoldersPage(driver);
		 tp.CreateFolder(foldername,"Default Security Group");
		 ss = new	 SessionSearch(driver); 
		 ss.basicContentSearch("crammer");
		 ss.bulkFolderExisting(foldername);
		 
		 tp.CreateTagwithClassification(Tagname,"Privileged");
		
		page = new ProductionPage(driver);
		
	}
	   
		
	
		@Test(groups={"smoke","regression"})
	    public void AddNewProduction() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		   page.CreateProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
	  
	  }
      
	   
       @Test(groups={"smoke","regression"})
	   public void Productionwithallredaction() throws Exception {
	   System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
	   page.Productionwithallredactions(productionname, PrefixID, SuffixID, foldername, Tagname);
	  
	  }
      
      
      
  		@Test(groups={"smoke","regression"})
	    public void ExportwithpriorProduction() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	    page.ExportwithpriorProduction(exportname, PrefixID, SuffixID, foldername);
   	  }
	   
		 @Test(groups={"regression"})
		 public void ProductionwithNatives() throws Exception {
		 System.out.println("******Execution started for ProductionwithNatives********");
		 page.ProductionwithNatives(productionname1, PrefixID, SuffixID, foldername,templatername);
	  }
		 
		 @Test(groups={"regression"})
		 public void ProductionwithTechIssuetags() throws ParseException, InterruptedException, IOException {
		 System.out.println("******Execution started for ProductionwithTechIssuetags********");
		 tp.CreateTagwithClassification(Tagnametech,"Technical Issue");
		 ss.basicContentSearch("crammer");
		 ss.bulkTagExisting(Tagnametech);
		 page.ProductionwithTechIssuetags(productionname1, PrefixID, SuffixID, foldername,Tagnameprev,Tagnametech);
	  }
	 	
	   //added by Narendra	
	   @Test(groups={"smoke","regression"})
	   public void Filter() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionFilter();
	  
	  }
	   
	   @Test(groups={"smoke","regression"})
	   public void Sort() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionSort();
	  
	  }
	  
	   @Test(groups={"smoke","regression"})
	   public void Grid() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.GridView();
	  
	  }
	    
	   @Test(groups={"smoke","regression"})
	   public void VerifyTemplate() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String tempName = "newProd"+Utility.dynamicNameAppender();
		page.SaveTemplate(tempName);
	  
	  }
	   
	   @Test(groups={"smoke","regression"})
	   public void DeleteTemplate() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String tempName = "newProd"+Utility.dynamicNameAppender();
		page.DeleteTemplate(tempName);
	  
	  }
	   
	   @Test(groups={"smoke","regression"})
	   public void VerifyProductionSet() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String prodsetName = "newSet"+Utility.dynamicNameAppender();
		page.CreateProductionSets(prodsetName);
	  
	  }
      
	  @Test(groups={"smoke","regression"})
	   public void LockProduction() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		page.ProductionLock();
	  
	  }
	  
	  @Test(groups={"smoke","regression"})
	   public void CustomizedTemplate() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		String productionName = "newSet"+Utility.dynamicNameAppender();
		String newproductionName = "productionName"+Utility.dynamicNameAppender();
		page.CustomizeTemplate(productionName,newproductionName);
	  
	  }
	
	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		System.out.println("------------------------------------------");
	    System.out.println("Executing method : " + testMethod.getName());       
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
		try{ 
			lp.logout();
		     //lp.quitBrowser();	
			}finally {
				lp.quitBrowser();
						}
		LoginPage.clearBrowserCache();
	}
}

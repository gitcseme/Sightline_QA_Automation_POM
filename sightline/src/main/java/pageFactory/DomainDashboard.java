package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class DomainDashboard {
	 Driver driver;
	 BaseClass base;
	 
	
	 
	 public Element getDataRefresh_info(){ return driver.FindElementByXPath("//*[@id='domainDashboardRefreshTime']"); }
	 public Element getAddComment1(){ return driver.FindElementById("1_textarea"); }
	 public Element getSaveDoc(){ return driver.FindElementById("Save"); }
	 public Element getprojectlink_GotoProject(){ return driver.FindElementByXPath("//*[@id='dropdownMenuLink' and contains(text(),"+Input.projectName+")]/following-sibling::ul//li[1]"); }
	 public Element getprojectnamelink(String projectname){ return driver.FindElementByXPath("(//*[@id='taskbasic']//a[contains(text(),'"+Input.projectName+"')])[1]"); }
	 public Element getdashboardtitle(){ return driver.FindElementById("domainDashboardTitle"); }
	 public Element getBacktodomaindashboard(){ return driver.FindElementByXPath("//a[@name='Back to the Domain Dashboard']"); }
	 public Element availableDomains(String domain){
		 return driver.FindElementByXPath("//ul[@id='ddlDomains']/li/a[contains(text(),'"+domain+"')]"); 
	}
	 
	 //add by Aathith
	 public Element getGearbtn(){
		 return driver.FindElementByXPath("//button[@class='ColVis_Button ColVis_MasterButton']"); 
	 }
	 public Element getExportbtn(){
		 return driver.FindElementByXPath("//i[@id='exporttoExcel']"); 
	 }
	 
	 public ElementCollection getprojectName(){
		 return driver.FindElementsByXPath("//*[@id='dropdownMenuLink']"); 
	 }
	 
	 public ElementCollection getTableHeader(){
		 return driver.FindElementsByXPath("//*[@id='dtDomainProjectList']/thead/tr/th");
	 }
	 
	 public ElementCollection getColumValue(int colum){
		 return driver.FindElementsByXPath("//*[@id='dtDomainProjectList']/tbody/tr/td["+colum+"]");
	 }
	 
	 public Element getColumTitle(String ColumName){
		 return driver.FindElementByXPath("//ul[@class='ColVis_collection']//label[@data-value='"+ColumName+"']/../../input");
	 }
	 
	 public Element getBackGround(){
		 return driver.FindElementByXPath("//div[@class='ColVis_collectionBackground']");
	 }
	 public Element getBullHornIcon() {
			return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
		}
	public Element getBullIcon() {
			return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']/following-sibling::b");
	}
	
	public Element getSavebtn() {
		return driver.FindElementByXPath("//i[@id='domainDashboardSaveReport']");
	}

	 
	 public DomainDashboard(Driver driver){

	        this.driver = driver;
	        //this.driver.getWebDriver().get(Input.url+ "DomainDashboard/DomainDashboard");
	        base = new BaseClass(driver);
	        //This initElements method will create all WebElements
	        //PageFactory.initElements(driver.getWebDriver(), this);
	  
	    }
	 
	 public void ImpersonatetoPAforDomainProject()
	 {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getprojectnamelink(Input.projectName).Visible()  ;}}), Input.wait60); 
			getprojectnamelink(Input.projectName).Click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 	
			base.selectproject();
		 
			getBacktodomaindashboard().waitAndClick(10);
			
		  Assert.assertTrue(getdashboardtitle().Displayed());
		 
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param element
	  * @return
	  * @Description get text from elementcollection 
	  */
	 public List<String> getColumValues(ElementCollection element) {
		 List<WebElement> elementList = null;
		 List<String> projectNames = new ArrayList<>();
		 elementList = element.FindWebElements();
		 for(WebElement project:elementList) {
			 String name = project.getText().trim();
			 projectNames.add(name);
		 }
		 return projectNames;
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param webproject
	  * @throws IOException
	  * @Description verify that project names are equl in webtable and downloaded xlsx file
	  */
	 public void verifyProjectName(List<String> webproject) throws IOException  
		{  
		FileInputStream fis=new FileInputStream(new File(base.GetFileName()));
		XSSFWorkbook wb=new XSSFWorkbook(fis);   
		XSSFSheet sheet=wb.getSheetAt(0);  
		int n = webproject.size();  
		int i =3;
		for( i=0;i<n;i++) 
		{  
			String projectName = sheet.getRow(i+3).getCell(0).toString();
			if(!webproject.get(i).equalsIgnoreCase(projectName)) {
				base.failedStep("project name not equal");
			}
		}  
		base.passedStep("project names exported correctly in xlsx");
		  
		} 
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param webproject
	  * @throws IOException
	  * @Description verify that custodian value are equl in webtable and downloaded xlsx file
	  */
	 public void verifyCustodianValues(List<String> webproject) throws IOException  
		{  
		FileInputStream fis=new FileInputStream(new File(base.GetFileName()));
		XSSFWorkbook wb=new XSSFWorkbook(fis);   
		XSSFSheet sheet=wb.getSheetAt(0);  
		int n = webproject.size();  
		int i =3;
		for( i=0;i<n;i++) 
		{  
			String projectName = sheet.getRow(i+3).getCell(6).toString();
			if(!webproject.get(i).equalsIgnoreCase(projectName)) {
				base.failedStep("Custotian values name not equal");
			}
		}  
		base.passedStep("Custotian values exported correctly in xlsx");
		  
		}
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param colums
	  * @Description add or remove colum from webtable in domaindashboard
	  */
	 public void AddOrRemoveColum(String[] colums) {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getGearbtn());
		 driver.scrollingToElementofAPage(getGearbtn());
		 getGearbtn().waitAndClick(10);
		 for(String colum:colums) {
			 driver.waitForPageToBeReady();
			 getColumTitle(colum).ScrollTo();
			 getColumTitle(colum).waitAndClick(10);
			 
		 }
		 getBackGround().waitAndClick(10);
		 
	 }
		  
	 /**
	  * @author Aathith.Senthilkumar
	  * @param total
	  * @param db
	  * @param index
	  * @param workspace
	  * @Desctiption verify that total utilazied space is equl to database size plus index size plus workspace size
	  */
	 public void verifyTotalUtilizedDiskSpace(List<String> total, List<String> db, List<String> index, List<String> workspace) {
		 int n = total.size();
		 for(int i=0;i<n;i++) {
			 double sum = Double.parseDouble(total.get(i));
			 double a = Double.parseDouble(db.get(i));
			 double b = Double.parseDouble(index.get(i));
			 double c = Double.parseDouble(workspace.get(i));
			 if(sum!=(a+b+c)) {
				 base.failedStep("total size is mismatch");
			 }
		 }
		 base.passedStep("verified Total project size including the database size, index size and workspace size of the project");
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description wait till the file is download
	  */
	 public void waitForFileDownload() {
		 driver.waitForPageToBeReady();
		 if(base.GetFileName().contains("tmp")) {
			 base.waitTime(1);
			 waitForFileDownload();
		}
	 }
	    
}

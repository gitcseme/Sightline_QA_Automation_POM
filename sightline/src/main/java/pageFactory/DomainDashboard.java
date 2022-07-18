package pageFactory;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class DomainDashboard {
	 Driver driver;
	 BaseClass base;
	 SoftAssert softAssertion;
	
	 
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
	
	public Element getInactiveProjectToggle() {
		return driver.FindElementByXPath("//input[@id='showDeactiveProjects']/../i");
	}
	
	public Element getActiveprojectTableValue(String text) {
		return driver.FindElementByXPath("//table[@id='taskbasic']//*[contains(text(),'"+text+"')]");
	}

	public Element getToatalProjectCount() {
		return driver.FindElementByXPath("//div[text()='TOTAL PROJECTS']/preceding-sibling::div");
	}
	
	public Element getActiveProjectCount() {
		return driver.FindElementByXPath("//div[text()='ACTIVE PROJECTS']/preceding-sibling::div");
	}
	
	public Element getTableValue(int row, int colum){
		 return driver.FindElementByXPath("//*[@id='dtDomainProjectList']/tbody/tr["+row+"]/td["+colum+"]");
	}
	
	public Element getNextBtn() {
		return driver.FindElementByXPath("//a[text()='Next']");
	}
	
	public Element getNextbuttonDisabledStatus() {
		return driver.FindElementByXPath("//li[@class='paginate_button next disabled']");
	}
	
	public Element getOneTableHeader(int colum) {
		return driver.FindElementByXPath("//*[@id='dtDomainProjectList']/thead/tr/th["+colum+"]");
	}
	 
	public Element getCreateNewProjectBtn() {
		return driver.FindElementByXPath("//a[@id='createProject']");
	}
	
	public Element getProjectName(){ 
		return driver.FindElementById("txtproject"); 
	}
	
	public Element getButtonSaveProject(){ 
		return driver.FindElementById("btnSaveProject"); 
	}
	
	public Element getSearchProject(){ 
		return driver.FindElementById("txtSearch"); 
	}
	
	public Element getHyperLinkOnProject(String projectName) {
		return driver.FindElementByXPath("//td[@class='ddGridAlignLeft sorting_1']//a[text()='"+projectName+"']");
	}
	
	public Element getDeactivateProject(String projectName) {
		return driver.FindElementByXPath("//a[text()='"+projectName+"']/..//a[text()='Deactivate Project']");
	}
	
	public Element getFirstHyperLink() {
		return driver.FindElementByXPath("(//a[@id='dropdownMenuLink'])[1]");
	}
	
	public Element getFirstGoToProject() {
		return driver.FindElementByXPath("(//a[text()='Go to Project'])[1]");
	}
	
	public Element getPopupMsg() {
		return driver.FindElementByXPath("//span[@class='MsgTitle']/../p");
	}
	
	public Element getAscending() {
		return driver.FindElementByXPath("//th[@aria-sort='ascending']");
	}
	
	public Element getDescending() {
		return driver.FindElementByXPath("//th[@aria-sort='descending']");
	}
	
	public Element getYesBtn() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}
	
	public Element getNoBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	
	public ElementCollection getCheckBox() {
		return driver.FindElementsByXPath("//li/label/input");
	}
	
	public Element getNotificationMsg() {
		return driver.FindElementByXPath("(//ul[@class='notification-body']//a)[1]");
	}

	
	 public DomainDashboard(Driver driver){

	        this.driver = driver;
	        //this.driver.getWebDriver().get(Input.url+ "DomainDashboard/DomainDashboard");
	        base = new BaseClass(driver);
	        softAssertion = new SoftAssert();
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
		 waitForDomainDashBoardIsReady();
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
			 double tsum = a+b+c;
			 if((int)sum!=(int)tsum) {
				 base.failedStep(sum+"total size is mismatch"+tsum);
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
	 /**
	  * @author Aathith.Senthilkumar
	  * @param colums
	  * @Description verify the column available in domain dashboard table
	  */
	 public void isAllColumsAreAvailable(String[] colums) {
		 driver.waitForPageToBeReady();
		 List<String> tableheadervalues = getColumValues(getTableHeader());
		 int n = colums.length;
		 for(int i=0;i<n;i++) {
			 softAssertion.assertEquals(colums[i], tableheadervalues.get(i));
		 }
		 softAssertion.assertAll();
		 base.passedStep("All colums are available as expected sequance");
	 }
	    
	 /**
	  * @author Aathith.Senthilkumar
	  * @return
	  * @Description verify that is inactive project is available in the domain
	  */
	 public boolean isInactiveProjectAvailable() {
		 int total = Integer.parseInt(getToatalProjectCount().getText().trim());
		 int active = Integer.parseInt(getActiveProjectCount().getText().trim());
		 if(total>active)
			 return true;
		 return false;
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @return inactive project name
	  * @Description get name inactive project name
	  */
	 public String getInactiveProjectName() {
		 
		 String projectname = null;
		 getInactiveProjectToggle().waitAndClick(10);
		 int n = base.getIndex(getTableHeader(), "STATUS");
		 getOneTableHeader(n).waitAndClick(10);
		 driver.waitForPageToBeReady();
		 base.waitForElementCollection(getColumValue(n));
		 List<WebElement> element = getColumValue(n).FindWebElements();
		 int i = 1;
		 driver.waitForPageToBeReady();
		 for(WebElement ele : element) {
			 driver.waitForPageToBeReady();
			 String isActive = ele.getText();
			 if(isActive.equals("Inactive")) {
				 int m =base.getIndex(getTableHeader(), "PROJECT NAME");
				 projectname = getTableValue(i, m).getText().trim();
			 }
			 i++;
		 }
		 if(projectname==null) {
			 if(!getNextbuttonDisabledStatus().isElementAvailable(1)) {
				 getNextBtn().waitAndClick(10);
				 getOneTableHeader(n).waitAndClick(10);
				 driver.waitForPageToBeReady();
				 getInactiveProjectName();
			 }else {
				 base.failedStep("verification failed");
			 }
		 }
		 return projectname;
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Descrioption verify that is inactive and active project are available in the list
	  */
	 public void isActiveInactiveListed() {
		 
		 getInactiveProjectToggle().waitAndClick(10);
		 int n = base.getIndex(getTableHeader(), "STATUS");
		 getOneTableHeader(n).waitAndClick(10);
		 driver.waitForPageToBeReady();
		 base.waitForElementCollection(getColumValue(n));
		 List<WebElement> element = getColumValue(n).FindWebElements();
		 driver.waitForPageToBeReady();
		 for(WebElement ele : element) {
			 driver.waitForPageToBeReady();
			 String isActive = ele.getText();
			 if(isActive.equals("Inactive")) {
				 base.passedStep("inactive project is listed");
				 break;
			 }
		 }
		 for(WebElement ele : element) {
			 driver.waitForPageToBeReady();
			 String isActive = ele.getText();
			 if(isActive.equals("Active")) {
				 base.passedStep("Active project is listed");
				 break;
			 }
		 }
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param colums
	  * @Description verify that column title is available
	  */
	 public void isTitleIsAvailable(String colums) {
		 driver.waitForPageToBeReady();
		 boolean flag = true;
		 List<String> tableheadervalues = getColumValues(getTableHeader());
		 for(String colum:tableheadervalues) {
			 if(colum.equalsIgnoreCase(colums)) {
				 flag = false;
				 base.passedStep(colums+" is available in Table");
				 break;
			 }
		 }
		 if(flag) {
			 base.failedStep(colums+"colum is not available");
		 }
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description natigate to domainDashBoard Page
	  */
	 public void naviageToDomainDashBoardPage() {
		 try {
			 this.driver.getWebDriver().get(Input.url+ "DomainDashboard/DomainDashboard");
		 }catch(Exception e) {
			 base.failedStep(e.getMessage()+" navigate to domainDashBoardPage failed");
		 }
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param projectName
	  * @Description Deactivate a project
	  */
	 public void deactivateProject(String projectName) {
		 naviageToDomainDashBoardPage();
		 driver.waitForPageToBeReady();
		 filterProject(projectName);
		 
		 driver.waitForPageToBeReady();
		 base.waitForElement(getHyperLinkOnProject(projectName));
		 driver.scrollingToElementofAPage(getHyperLinkOnProject(projectName));
		 getHyperLinkOnProject(projectName).waitAndClick(10);
		 base.waitForElement(getDeactivateProject(projectName));
		 getDeactivateProject(projectName).waitAndClick(10);
		 base.getNOBtn().waitAndClick(10);
		 
		 clearProjectSearchFilter();
		 base.stepInfo("deactivated a project");
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param projectName
	  * @Description filter the project using projectName
	  */
	 public void filterProject(String projectName) {
		 driver.waitForPageToBeReady();
		 waitForDomainDashBoardIsReady();
		 base.waitForElement(getSearchProject());
		 driver.scrollingToElementofAPage(getSearchProject());
		 //getSearchProject().waitAndClick(5);
		 getSearchProject().SendKeys(projectName+Keys.ENTER);
		 base.hitKey(KeyEvent.VK_ENTER);
		 //base.stepInfo(projectName+" filter the project");
		 driver.waitForPageToBeReady();
		 if(!base.text(projectName).isDisplayed()) {
			 filterProject(projectName);
		 }
		 base.stepInfo(projectName+" filter the project");
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description clear the project search filter
	  */
	 public void clearProjectSearchFilter() {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getSearchProject());
		 getSearchProject().waitAndClick(5);
		 getSearchProject().Clear();
		 base.hitKey(KeyEvent.VK_ENTER);
		 base.stepInfo("clear the project filter");
	 }

	 /**
	  * @author Aathith.Senthilkumar
	  * @param projectname
	  * @Description create a project using domain credential 
	  */
	 public void create_a_project_From_Domain(String projectname) {
		 driver.waitForPageToBeReady();
		 base.waitForElement(getCreateNewProjectBtn());
		 getCreateNewProjectBtn().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 base.waitForElement(getProjectName());
		 getProjectName().SendKeys(projectname);
		 driver.scrollingToBottomofAPage();
		 driver.waitForPageToBeReady();
		 base.waitForElement(getButtonSaveProject());
		 getButtonSaveProject().waitAndClick(10);
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @return
	  * @Description Create a project and Deactivate that project
	  */
	 public String createAndDeactivateProject() {
		 String projectName = "AAA"+Utility.dynamicNameAppender();
		 if(!isInactiveProjectAvailable()) {
				base.clearBullHornNotification();
				create_a_project_From_Domain(projectName);
				base.waitForNotification();
				naviageToDomainDashBoardPage();
				deactivateProject(projectName);
				base.stepInfo("domain project was created and deactivated the project");
		 }
		 return projectName;
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Desctiprion click on inactive project name and verify it's not a hyper link
	  */
	 public void clickInactiveProjectName() {
		 
		 getInactiveProjectToggle().waitAndClick(10);
		 int n = base.getIndex(getTableHeader(), "STATUS");
		 getOneTableHeader(n).waitAndClick(10);
		 
		 driver.waitForPageToBeReady();
		 base.waitForElementCollection(getColumValue(n));
		 List<WebElement> element = getColumValue(n).FindWebElements();
		 
		 int i = 1;
		 driver.waitForPageToBeReady();
		 for(WebElement ele : element) {
			 driver.waitForPageToBeReady();
			 String isActive = ele.getText();
			 if(isActive.equals("Inactive")) {
				 int m =base.getIndex(getTableHeader(), "PROJECT NAME");
				 getTableValue(i, m).Click();
				 if(base.text("Domain:").isDisplayed()){
					 base.passedStep("Inactive project name is a label and not link");
				 }else {
					 base.failedStep("is not in domain dashboard");
				 }
				 break;
			 }
			 i++;
		 }
		 
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Descripiton verify only Active project is listed
	  */
	 public void verifyOnlyActiveProjectListed() {
		 
		 int n = base.getIndex(getTableHeader(), "STATUS");
		 driver.waitForPageToBeReady();
		 base.waitForElementCollection(getColumValue(n));
		 List<WebElement> element = getColumValue(n).FindWebElements();
		 driver.waitForPageToBeReady();
		 for(WebElement ele : element) {
			 driver.waitForPageToBeReady();
			 String isActive = ele.getText();
			 if(isActive.equals("Inactive")) {
				 base.failedStep("in active project available");
			 }
		 }
		 base.passedStep("only active project is listed");
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @param projectName
	  * @Description go to the progress on the alert message do you deactivate a project
	  */
	 public void doYouDeactivateProject(String projectName) {
		 driver.waitForPageToBeReady();
		 waitForDomainDashBoardIsReady();
		 filterProject(projectName);
		 
		 driver.waitForPageToBeReady();
		 base.waitForElement(getHyperLinkOnProject(projectName));
		 driver.scrollingToElementofAPage(getHyperLinkOnProject(projectName));
		 base.waitTillElemetToBeClickable(getHyperLinkOnProject(projectName));
		 getHyperLinkOnProject(projectName).waitAndClick(10);
		 base.waitForElement(getDeactivateProject(projectName));
		 getDeactivateProject(projectName).waitAndClick(10);
		 base.stepInfo("Do you really wants to deactivate a project ?");
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description wait for domain dash board page to load completely 
	  */
	 public void waitForDomainDashBoardIsReady() {
			base.text("Active Projects").isElementAvailable(30);
			base.text("Active Projects Utilization").isElementAvailable(30);
			base.text("Users in Active Projects").isElementAvailable(30);
	}
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description verify user able to the table as ascending and descending order
	  */
	 public void verifyAscendingDescendingOrder() {
			driver.waitForPageToBeReady();
			waitForDomainDashBoardIsReady();
			if(getAscending().isElementAvailable(10)) {
				base.passedStep("Web table is sorted in Ascending ordrer");
				getAscending().waitAndClick(10);
				driver.waitForPageToBeReady();
				waitForDomainDashBoardIsReady();
				if(getDescending().isElementAvailable(10)) {
					base.stepInfo("Web table s in descending order");
					base.passedStep("User is able to sort the column by ascending/descending order.");
				}else {
					base.failedStep("verification failed");
				}
			}else {
				base.stepInfo("Web table is sorted in Desscending ordrer");
				getDescending().waitAndClick(10);
				driver.waitForPageToBeReady();
				waitForDomainDashBoardIsReady();
				if(getAscending().isElementAvailable(10)) {
					base.stepInfo("Web table s in Ascending order");
					base.passedStep("User is able to sort the column by ascending/descending order.");
				}else {
					base.failedStep("verification failed");
				}
			}
		}
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description enable display Inactive project
	  */
	 public void enableInActiveProject() {
			try{
				driver.waitForPageToBeReady();
				waitForDomainDashBoardIsReady();
				base.waitForElement(getInactiveProjectToggle());
				driver.scrollingToElementofAPage(getInactiveProjectToggle());
				getInactiveProjectToggle().waitAndClick(10);
				base.stepInfo("Display enabled for inactive project");
			}catch(Exception e) {
				base.failedStep(e.getMessage());
			}
		}
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description check domainDashBoard table is in default order or not, if not in default order make it as a default order
	  */
	 public void makeDomainDashBoardTableDefaultOrder() {
		 
		 String[] addcolumns = {"ProjectLabel", "IsActive", "LastUpdatedOn", "NoOfCustodian",
				 "NoOfPublishedDocument", "NoOfReleasedDocument", "CorpClient",
				 "ProjectCreatedOn", "ProjectCreatedBy", "TotalUtilizedDiskSize"};
		 if(!isAllColumnsAreInDefaultOrder()) {
			 removeSelectedCheckBox();
			 AddOrRemoveColum(addcolumns);
			 getSavebtn().waitAndClick(10);
		 }
		 
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @return boolean
	  * @Description verify table header is default order
	  */
	 public boolean isAllColumnsAreInDefaultOrder() {
		 String[] colums = {"PROJECT NAME","STATUS","DATA AS OF (UTC)","CUSTODIANS (#)",
					"DOCS PUBLISHED (#)","DOCS RELEASED (#)","CORPORATE CLIENT","CREATED DATE",
					"CREATED BY","TOTAL UTILIZED DISK SIZE (GB)"};
		 driver.waitForPageToBeReady();
		 waitForDomainDashBoardIsReady();
		 List<String> tableheadervalues = getColumValues(getTableHeader());
		 int n = colums.length;
		 for(int i=0;i<n;i++) {
			 //System.out.println(colums[i]+" vs "+tableheadervalues.get(i));
			 if(!colums[i].equalsIgnoreCase(tableheadervalues.get(i))) {
				 return false;
			}
		 }
		 return true;
	 }
	 
	 /**
	  * @author Aathith.Senthilkumar
	  * @Description remove all selected check box
	  */
	 public void removeSelectedCheckBox() {
		 driver.waitForPageToBeReady();
		 waitForDomainDashBoardIsReady();
		 base.waitForElement(getGearbtn());
		 driver.scrollingToElementofAPage(getGearbtn());
		 getGearbtn().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 List<WebElement> element = getCheckBox().FindWebElements();
		 for(WebElement ele : element) {
			 if(ele.isSelected()) {
				 driver.waitForPageToBeReady();
				 ele.click();
			 }
		 }
		 getBackGround().waitAndClick(10);
	 }
	 public void getNotificationMessage(int bgCountBefore, String projectName) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.initialBgCount() == bgCountBefore + 1;
				}
			}), Input.wait120);
			final int bgCountAfter = base.initialBgCount();

			if (bgCountAfter > bgCountBefore) {
				getBullHornIcon().waitAndClick(10);

				String downloadMsg = getNotificationMsg().getText();
				String expected = "Project " + projectName + " creation successful.";
				String failMsg = "Download Notification is not As Expected";
				base.textCompareEquals(downloadMsg, expected, downloadMsg, failMsg);
			} else {
				driver.Navigate().refresh();
			}
		}
}

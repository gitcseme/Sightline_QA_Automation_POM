package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class Categorization {

    Driver driver;
    
    public Element getSelectIdentifyByTags(){ return driver.FindElementByPartialLinkText("Identify by Tag"); }
    public Element getSelectTag(String tagName){ return driver.FindElementByXPath("//div[@id='tags']//a[@data-content='"+tagName+"']"); }
    public Element getGotoStep2(){ return driver.FindElementByXPath("//button[@id='SelectDocs']"); }
    public Element  getAnalyzeSelectFolders(){ return driver.FindElementByLinkText("Analyze Select Folders"); }
    public Element getFolderSelectionPopUp(){ return driver.FindElementByXPath("//button[@id='btnFolder']"); }
    public Element getSelectFolder(String folderName){ return driver.FindElementByXPath("//div[@id='divTree']//a[@data-content='"+folderName+"']"); }
    public Element getSelectBtn(){ return driver.FindElementByXPath("//button[@id='btnSelect']"); }
    public Element getRun(){ return driver.FindElementByXPath("//button[@id='btnRun']"); }
    public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='btnYes']"); }
    public Element getResults(){ return driver.FindElementByXPath("//*[@id='divConfiguration']/div[2]/div[2]/div[1]"); }
    public ElementCollection getTree(){ return driver.FindElementsByXPath("//a[@class='jstree-anchor'][contains(text(),'')]"); }
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
    public ElementCollection getCatFolderTree(){ return driver.FindElementsByXPath("(//div[@class='panel-body'])[6]//a"); }
       
    public Categorization(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Proview/Proview");
        driver.waitForPageToBeReady();
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    
    public void selectTagInCat(String tagName) {
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getTree().Visible()  ;}}), Input.wait30); 
    	 System.out.println(getTree().FindWebElements().size());
    		for (WebElement iterable_element : getTree().FindWebElements()) {
    			//System.out.println(iterable_element.getText());
    			if(iterable_element.getText().contains(tagName)){
    				try {
    					Thread.sleep(5000);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
    				driver.scrollingToBottomofAPage();
    		//		System.out.println(iterable_element.getText());
    				iterable_element.click();
    			}
    		}

	}

    public int runCatWithTagsAndFolders(final String tagName,final String folderName) throws InterruptedException{

    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSelectIdentifyByTags().Visible() ;}}),Input.wait30);
    	
    	getSelectIdentifyByTags().Click();
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectTag(tagName).Exists() ;}}),Input.wait30);
    	/*getSelectTag(tagName).ScrollTo();*/
    	
    	selectTagInCat(tagName);
    	driver.scrollingToBottomofAPage();
    	
    	
    	getGotoStep2().Click();
    	
    	
    	getAnalyzeSelectFolders().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFolderSelectionPopUp().Visible() ;}}),Input.wait30);
    	getFolderSelectionPopUp().Click();
    //
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getCatFolderTree().Visible()  ;}}), Input.wait30); 
    	 System.out.println(getCatFolderTree().FindWebElements().size());
    		for (WebElement iterable_element : getCatFolderTree().FindWebElements()) {
    			//System.out.println(iterable_element.getText());
    			if(iterable_element.getText().contains(folderName)){
    				try {
    					Thread.sleep(5000);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
    				driver.scrollingToBottomofAPage();
    		//		System.out.println(iterable_element.getText());
    				iterable_element.click();
    			}
    		}
    	//
    	/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectFolder(folderName).Visible() ;}}),Input.wait30);
    	getSelectFolder(folderName).Click();*/
    	
    	getSelectBtn().Click();
    	
    	final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
    	getRun().Click();
    	//driver.scrollingToBottomofAPage();
    	try{
   		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   				getPopupYesBtn().Visible() || getResults().getText()!=null ;}}),Input.wait30);
   		getPopupYesBtn().Click();  
   	
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   getResults().getText().matches("-?\\d+(\\.\\d+)?") ;}}),Input.wait60);
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 	 			bc.initialBgCount() == Bgcount+1  ;}}),Input.wait60);  
    	return Integer.parseInt(getResults().getText());
    	
    	
    }

}
package pageFactory;

import java.util.concurrent.Callable;

import automationLibrary.Driver;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;
import automationLibrary.Element;
public class DataSets {
	
	Driver driver;
	SessionSearch search;
	BaseClass base;

	


	public Element getDatasetBtn() {
		return driver.FindElementByXPath("//a[@title='Datasets']");
	}
	
	public Element getSearchTheFile() {
		return driver.FindElementByXPath("//input[@id='searchBox']");
	}
	
	public Element getClkSearch() {
		return driver.FindElementByXPath("//i[@id='btnSearchBox']");
	}
	
	public Element getSelectAction() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}
	public Element getSelectDocList() {
		return driver.FindElementByXPath("//a[@id='idBulkTag'][text()='DocList']");
	
	}
	

	public DataSets(Driver driver) {

		this.driver = driver;
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
	}
	
	public void fillingDataSetPage() {
		
	driver.WaitUntil((new Callable<Boolean>() {
		   public Boolean call() {
		   return getDatasetBtn().Enabled();
		   }}), Input.wait30);getDatasetBtn().Click();	
		
		
	driver.WaitUntil((new Callable<Boolean>() {
		    public Boolean call() {
			return getSearchTheFile().Enabled();
			}}), Input.wait30);getSearchTheFile().Click();	
			getSearchTheFile().SendKeys("42770");
	
	driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
			return getClkSearch().Enabled();
			}}), Input.wait30);getClkSearch().Click();	
				
   driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
			return getSelectAction().Enabled();
			}}), Input.wait30);getSelectAction().Click();	
					
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
				return getSelectDocList().Enabled();
				}}), Input.wait120);getSelectDocList().waitAndClick(10);	
				base.stepInfo("Dataset Page is Filled");					
				
	}				
			
}




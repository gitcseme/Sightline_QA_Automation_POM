package pageFactory;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import SightlineObjects.DatasetDetails;
import SightlineObjects.DatasetTileDetails;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import automationLibrary.GenFunc;
import testScriptsSmoke.Input;

public class ICE_DatasetsPage {
	
	Driver driver;
	BaseClass bc = new BaseClass(driver);
	
	public String getDatasetPageTitle() {return driver.FindElementByClassName("page-title").getText();}
	public boolean isDatasetActive() {return driver.FindElementByCssSelector("#LeftMenu > li:nth-child(2)").GetAttribute("class").equalsIgnoreCase("Active");}
	public Element getDatasetPage() {return driver.FindElementByCssSelector("#cardCanvas");}
	public ElementCollection DocViewDocumentList() {return driver.FindElementsByCssSelector("#dtDocList > tbody > tr");}
	/*Page related Elements*/
	public Element getShowDropDown(){return driver.FindElementByCssSelector("#DatasetTypeList");}
	public ElementCollection getShowDropDownOptions(){return driver.FindElementsByCssSelector("#DatasetTypeList > option");}
	public Element getCreateNewUploadSetLink(){return driver.FindElementByCssSelector("#createSet");}
	public Element getCreateNewMappedSetLink() {return driver.FindElementByCssSelector("#createMappedSet");}
	public Element getSearchByBox() {return driver.FindElementByCssSelector("#searchBox");}
	public Element getSearchBoxBtn() {return driver.FindElementByCssSelector("#btnSearchBox");}
	public ElementCollection getSortByOptions() {return driver.FindElementByCssSelector("#SortBy").getOptions();}
	public Element getSortBy() {return driver.FindElementByCssSelector("#SortBy");}
	public ElementCollection getDatasets() {return driver.FindElementsByCssSelector("#dataset_tilesContainer > li");}
	public Element getLoadMoreBtn() {return driver.FindElementById("btnLoadDataset");}
	//public Element getTotalDocumentsEl(){return driver.FindElementByCssSelector("#cardGrid > div > div > div:nth-Child(3) > div > strong");}
	public Element getTotalDocumentCount(){return driver.FindElementByCssSelector("#cardGrid > div > div > div:nth-Child(3) > div:nth-child(2) > strong");}
	
	/*Dataset tile related elements*/
	public Element getDatasetNamelink(Element dataset){return dataset.FindElementBycssSelector("span.pTime > a");}
	public Element getErrorCtLink(Element dataset) {return dataset.FindElementBycssSelector("div.errorCt > span");}
	public ElementCollection getDatasetDropdownMenuOptions(Element dataset) {return dataset.FindElementBycssSelector("ul.datasets-dropdown-menu").getLis();}
	public Element getSettingsDropdown(Element dataset) {return dataset.FindElementBycssSelector("div.actionBtn > a");}
	public Element getManageUploadBtn(Element dataset) {return dataset.FindElementBycssSelector("span:nth-child(5) > a");}
	
	/*New Dataset popup related Elements*/
	public Element getCreateDatasetPopup() {return driver.FindElementByCssSelector("div.ui-dialog");} //getattribute("display") - if none, popup is closed if block it is open.
	public Element getDatasetNameTxtBox() {return getCreateDatasetPopup().FindElementBycssSelector("#txtDatasetName");}
	public Element getCustodianNameTxtBox() {return getCreateDatasetPopup().FindElementBycssSelector("#txtCustodianName");}
	public ElementCollection getCustodianNameList() {return getCreateDatasetPopup().FindElementBycssSelector("#datalistCustodianName").getOptions();}
	public Element getDescriptionTxtBox() {return getCreateDatasetPopup().FindElementBycssSelector("#txtDatasetDescription");}
	public Element getCancelBtn() {return getCreateDatasetPopup().FindElementBycssSelector("#CancelUploadSet");}
	public Element getCreateBtn() {return getCreateDatasetPopup().FindElementBycssSelector("#CreateUploadSet");}
	
	/*Elements added by shilpi as part of fixes */
	public Element getdatasetleftmenuBtn() {return driver.FindElementByXPath("//*[@name='DataSets']");}
	public Element getTotalDocumentsEl(){return driver.FindElementByXPath("//*[@id='cardGrid']//div[3]/div[1]/strong");}

	
	
	
	public ICE_DatasetsPage (Driver driver)
	{
		this.driver = driver;
	}
		
	public Element getDatasetBtnLMenu()
	{
		ElementCollection menuOptions = driver.FindElementsByXPath(".//*[@id='LeftMenu']/li");
		for(Element option : menuOptions)
		{
			try{
			if(option.FindElementByXPath("//*[@name='DataSets']").GetAttribute("title").equalsIgnoreCase("Datasets"))
			{
				return option;
			}}
			catch(Exception e)
			{
				return null;
			}
			
		}
		return null;
	}
	
	
	public int getDatasetBtnLMenuPosition()
	{
		int position = 0;
		ElementCollection menuOptions = driver.FindElementsByCssSelector("#LeftMenu > li");
		for(Element option : menuOptions)
		{
			position++;
			if(option.FindElementBycssSelector("a.a-menu").GetAttribute("title").equalsIgnoreCase("Datasets"))
			{
				return position;
			}
		}
		return 0;
	}
	
	public Element getDatasetByName(String datasetName) throws InterruptedException 
	{	
		driver.waitForPageToBeReady();
		this.getSearchByBox().SendKeys(datasetName);
		this.getSearchBoxBtn().WaitUntilPresent().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDatasets().size()==1  ;}}),Input.wait60);	
			return this.getDatasets().getElementByIndex(0);
	}
	

	public ElementCollection getDatasetsByCustodianName(String custodianName) 
	{	
		System.out.println("*********Finding dataset by custodian name*********");
		ElementCollection datasets = getDatasets();
			for(Element dataset: datasets)
			{
				if (dataset.FindElementBycssSelector("span.pName").getText().toString() != custodianName)
				{
					datasets.remove(dataset);
				}
			}
			if(datasets.size()>0)
			{
				System.out.println("*********Found dataset by custodian name*********");
				return datasets;
			}else
			{
				System.out.println("*********Unable to find dataset by custodian name*********");
				new Exception("We are not able to find any datasets with provided custodian name, please check and run this again.");
				return null;
			}
	}

	public Boolean CreateNewUploadSet(DatasetDetails dd)
	{

		System.out.println("********Started to create new uploaded set.********");
	
		if(dd.getDatasetName() == null || dd.getDatasetName().isEmpty() || dd.getCustodianName()== null || dd.getCustodianName().isEmpty())
		{
			System.out.println("The details provided for creating dataset is not complete, please provide dataset name and cusotdian name cumpulsorily.");
			return false;
		}
		
		getCreateNewUploadSetLink().WaitUntilPresent().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDatasetNameTxtBox().Visible() ;}}),Input.wait60);
		
		getDatasetNameTxtBox().WaitUntilPresent().setText(dd.getDatasetName());
		
		getCustodianNameTxtBox().WaitUntilPresent().setText(dd.getCustodianName());
		
		if(dd.getDescription() != null || !dd.getDescription().isEmpty())
		{
			getDescriptionTxtBox().WaitUntilPresent().setText(dd.getDescription());
		}
		
		getCreateBtn().WaitUntilPresent().Click();
		
		try{
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				driver.FindElementByCssSelector("#mydropzone").Visible()  ;}}),Input.wait60);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		System.out.println("********Creation of new uploaded set is completed.********");
		return true;
	}
	
	@SuppressWarnings("unused")
	public boolean DeleteUploadedDatasetByName(String datasetName) throws InterruptedException
	{
		System.out.println("********Deletion of set is started.********");
		Element dataset = getDatasetByName(datasetName);
			dataset.ScrollTo();
		if(dataset!=null)
			{
			System.out.println("********Found dataset to delete.********");
				//getSettingsDropdown(dataset).ScrollTo();
				getSettingsDropdown(dataset).Click();
			
				getSettingsDropdown(dataset).FindElementByXPath("//*[@id='dataset_tilesContainer']/li/div[1]/dl/dt/a").WaitUntilPresent().Click();
				
				driver.waitForPageToBeReady();
				
				driver.FindElementById("bot1-Msg1").WaitUntilPresent().Click();
				
				driver.waitForPageToBeReady();
				
				driver.scrollPageToTop();
				
				getSearchByBox().WaitUntilPresent().setText(datasetName);
				
				Thread.sleep(1000);
				
				getSearchBoxBtn().WaitUntilPresent().Click();
				
				
					if(this.getDatasets().size()>0)
					{
						System.out.println("********Deletion of dataset failed.********");
						new Exception("The deletion of dataset is failed, please check this manually.");
						return false;
					}
					System.out.println("********Deletion of dataset is completed.********");
					return true;
			}	
		System.out.println("********Unable to find dataset to delete.********");
		return false;
		}
	
	public ElementCollection FilterDatasets(DatasetType dt)
	{
		System.out.println("********Filtering dataset started.********");		
		driver.FindElementById("DatasetTypeList").WaitUntilPresent().Click();
		ElementCollection filterOptions= getShowDropDownOptions();

		if(dt.equals(DatasetType.Mapped))
		{
			for(Element element : filterOptions)
			{
				if( element.getText().trim().contains("Mapped"))
				{
					element.WaitUntilPresent().Click();
					driver.waitForPageToBeReady();
					System.out.println("********Dataste filtered for Mapped********");
					break;
				}
			}
		}else if(dt.equals(DatasetType.Uploaded))
		{
			for(Element element : filterOptions)
			{
				if( element.getText().trim().contains("Uploaded"))
				{
					element.WaitUntilPresent().Click();
					driver.waitForPageToBeReady();
					System.out.println("********Dataste filtered for Uploaded********");
					break;
				}
			}
		}else if(dt.equals(DatasetType.All))
		{
			for(Element element : filterOptions)
			{
				if( element.getText().trim().contains("All"))
				{
					element.WaitUntilPresent().Click();
					driver.waitForPageToBeReady();
					System.out.println("********Removed filter from dataset********");
					break;
				}
			}
		}else
		{
			new Exception("Provided DatasetType to display is not valid, please provide valid DatasetType.");			
			return null;
		}
		return getDatasets();
	}
	
	public void sortBy(SortOptions sortby)
	{
		//need to write code for this section.
	}
	
	
	public void viewSetInDocViewByName(String datasetName) throws InterruptedException
	{
		System.out.println("********Finding dataset to view in DocView********");			
		Element dataset = getDatasetByName(datasetName);
		dataset.FindElementBycssSelector("#idAction").WaitUntilPresent().Click();
		dataset.FindElementBycssSelector("#idBulkFolder").WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		driver.getUrl().contains("/DocView");
		System.out.println("********Dataset in DocView completed********");
	}
	
	public void viewSetInDocListByName(String datasetName, boolean isDupeTest) throws InterruptedException
	{
		System.out.println("********Dataset in DocList started********");
		Element dataset = getDatasetByName(datasetName);
		dataset.FindElementBycssSelector("#idAction").WaitUntilPresent().Click();
		dataset.FindElementBycssSelector("#idBulkTag").WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		driver.getUrl().contains("/DocList");
		System.out.println("********Dataset in DocList completed********");
		if(isDupeTest)
			{driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				driver.FindElementByCssSelector("#btn-save-profile").Displayed()  ;}}),Input.wait60);	
		boolean AllCustodianFieldDisplayed = false;
		ElementCollection headers = driver.FindElementsByCssSelector("#dtDocList > thead > tr > th:nth-child(2) > tr > th");
		for(Element header:headers)
		{
			if(header.getText().equalsIgnoreCase("AllCustodians"))
			{AllCustodianFieldDisplayed = true;
				return;
			}
		}
		if(!AllCustodianFieldDisplayed)
		{
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.FindElementByCssSelector("#btnSelectColumn").Enabled() ;}}),Input.wait90);	
			driver.FindElementByCssSelector("#btnSelectColumn").WaitUntilPresent().Click();
			driver.FindElementByCssSelector("#hb2 > ul > li").WaitUntilPresent().Click();
			ElementCollection fields = driver.FindElementsByCssSelector("ul.list-unstyled > li");
			for(Element field:fields)
			{
				field.ScrollTo();
				if(field.getText().equalsIgnoreCase("AllCustodians"))
				{
					field.FindElementBycssSelector("label.checkbox").Click();
					driver.FindElementByCssSelector("#addFormObjects").Click();
					driver.FindElementByCssSelector("#btnUpdateColumns").Click();
					driver.waitForPageToBeReady();
				}
			}
		}
			}
	}
	
	public int getFieldPositionInDocListByName(String fieldName)
	{
		driver.waitForPageToBeReady();
		ElementCollection headers = driver.FindElementsByCssSelector("#dtDocList > thead > tr > th");
		int position =-1;
		for(Element header:headers)
		{
			position++;
			if(header.getText().equalsIgnoreCase("AllCustodians"))
			{
				return position;
			}
		}
		return position;
	}
	
	public void viewSetInTallyByName(String datasetName) throws InterruptedException
	{
		System.out.println("********Dataset in Tally started********");
		Element dataset = getDatasetByName(datasetName);
		dataset.FindElementBycssSelector("#idAction").WaitUntilPresent().Click();
		dataset.FindElementBycssSelector("#idBulkTally").WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		driver.getUrl().contains("/Tally");
		System.out.println("********Dataset in Tally completed********");
	}
	
	public DatasetTileDetails getDatasetTileDetailsByDataset(Element dataset)
	{
		System.out.println("********Get dataset details started********");
		DatasetTileDetails dd = new DatasetTileDetails();
		dd.setCustodianName(dataset.FindElementBycssSelector("span.pName").getText().trim());
		dd.setDatasetName(dataset.FindElementBycssSelector("span:nth-child(2) > a > strong").getText().trim());
		dd.setDatasetType(dataset.FindElementBycssSelector("span:nth-child(1)").getText().trim());
		dd.setErrorCount(GenFunc.StringToInt(dataset.FindElementBycssSelector("div.errorCt > span").getText().trim()));
		dd.setLastModifiedAt(GenFunc.StringToDate(dataset.FindElementBycssSelector("div.bottomStamps > div > div").getText().trim()));
		dd.setLastModifiedBy(dataset.FindElementBycssSelector("div.bottomStamps").FindElementByXPath("/div[1]/text()").getText().trim());
		dd.setLastStatus(dataset.FindElementBycssSelector("div.bottomStamps").FindElementByXPath("/div[2]/strong/text()[2]").getText().trim());
		dd.setLastStatusAt(GenFunc.StringToDate(dataset.FindElementBycssSelector("div.bottomStamps > div:nth-child(2) > div").getText().trim()));
		dd.setPublishedCount(GenFunc.StringToInt(dataset.FindElementBycssSelector("div.ingestCt > span").getText().trim()));
		dd.setUploadedCount(GenFunc.StringToInt(dataset.FindElementBycssSelector("div.sourceCt > span").getText().trim()));
		System.out.println("********Get dataset details completed********");
		return dd;
	}
	
	enum SortOptions
	{
		LastModifiedDate,
		LastStaus,
		LastModifiedUser,
		CustodianName
	}
	
	enum DatasetType
	{
		Uploaded,
		Mapped,
		All
	}

}

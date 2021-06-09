package pageFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import SightlineObjects.DatasetDetails;
import SightlineObjects.FileUploadDetails;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import automationLibrary.GenFunc;
import testScriptsSmoke.Input;

public class ICE_ManageUploadPage {
	
	Driver driver;
	String datasetName;
	
	public Element getDatasetDetailEditLink() {return driver.FindElementByCssSelector("#editUploadSet");}
	public Element getProjectPasswordEditLink() {return driver.FindElementByCssSelector("#editProjectPwd");}
	public Element getDropZoneLink() {return driver.FindElementByCssSelector("#mydropzone");}
	public Element getDropZoneStaticText() {return driver.FindElementByCssSelector("#mydropzone > div:nth-child(2)");}
	public Element getUploadFilesBtn() {return driver.FindElementByCssSelector("#initiate");}
	public ElementCollection getUploadedFilesPreview() {return driver.FindElementByCssSelector("#previews").getDivs();}
	public Element getRemoveFilefromUpload(Element file) {return file.FindElementBycssSelector("a.dz-remove");}
	public int getUploadCount() {return GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"));}
	public ElementCollection getNonProgressedFiles() {return driver.FindElementsByCssSelector("div.col-md-12.file-row:not(.dz-processing):not(.dz-success):not(.dz-error)");}
	public ElementCollection getCompletedFiles() {return driver.FindElementsByCssSelector("div.col-md-12.file-row.dz-processing.dz-success:not(.dz-error)");}
	public ElementCollection getInProgressFiles() {return driver.FindElementsByCssSelector("div.col-md-12.file-row.dz-processing:not(.dz-success):not(.dz-error)");}
	public Element getMessageText() {return driver.FindElementByCssSelector("#divbigBoxes > div > div > p");}
	
	/*Initate popup related Elements*/
	public Element getInitatePopup() {return driver.FindElementByCssSelector("#Msg1");}
	public Element getInitatePopupMessage() {return driver.FindElementByCssSelector("#Msg1 > div > p");}
	public Element getInitatePopupNoBtn() {return driver.FindElementByCssSelector("#bot2-Msg1");}
	public Element getInitatePopupYesBtn() {return driver.FindElementByCssSelector("#bot1-Msg1");}
	
	/*Dataset details edit pop-up related Elements*/
    public Element 	getEditDatasetPopup() {return driver.FindElementById("editUploadSetPopup");}
    public Element getDatasetNameTxtBox() {return getEditDatasetPopup().FindElementById("txtDatasetName");}
    public Element getCustodianNameTxtBox() {return getEditDatasetPopup().FindElementById("txtCustodianName");}
    public ElementCollection getCustodianList() {return getEditDatasetPopup().FindElementById("datalistCustodianName").getOptions();}
    public Element getDescriptionTxtBox() {return getEditDatasetPopup().FindElementById("txtDatasetDescription");}
    public Element getDatasetPopupCancelBtn() {return getEditDatasetPopup().FindElementById("CancelEditUploadSet");}
    public Element getDatasetPopupSaveBtn() {return getEditDatasetPopup().FindElementById("EditUploadSet");}
    
    /*ProjectPassword edit pop-up related Elements*/
    public Element getProjectPwdPopup() {return driver.FindElementById("editProjectPwdPopUp");}
    public Element getProjectPwdTxtBox() {return getProjectPwdPopup().FindElementById("txtProjectPwds");}
    public Element getProjectPwdCancelBtn(){return getProjectPwdPopup().FindElementById("CancelEditProjectPwd");}
    public Element getProjectPwdSaveBtn(){return getProjectPwdPopup().FindElementById("SaveProjectPwd");}
    
    /* Refresh popup related Elements*/
    public Element getConfirmNavigationPopup() {return driver.FindElementByCssSelector("div.MessageBoxMiddle");}
    public Element getConfirmNavigationPopupTitle() {return driver.FindElementByCssSelector("div.MessageBoxMiddle > span");}
    public Element getConfirmNavigationPopupMessage() {return driver.FindElementByCssSelector("div.MessageBoxMiddle > p");}
    public Element getConfirmNavigationPopupNoBtn() {return driver.FindElementByCssSelector("#bot2-Msg1");}
    public Element getConfirmNavigationPopupYesBtn() {return driver.FindElementByCssSelector("#bot1-Msg1");}
    
    public ICE_ManageUploadPage(Driver driver, String datasetName)
    {
    	this.driver = driver;
    	//Assert.assertTrue(driver.FindElementByClassName("page-title").getText().trim().equalsIgnoreCase("Dataset Upload: "+datasetName));
    }
    
    public void InitiateProcessing()
    {
    	if(driver.FindElementByCssSelector("#initiate").Enabled())
    	{
    		driver.FindElementByCssSelector("#initiate").WaitUntilPresent().Click();
    		driver.FindElementById("bot1-Msg1").WaitUntilPresent().Click();
    		driver.waitForPageToBeReady();
    		return;
    	}else
    	{
    		System.out.println("Initiate option is not enabled for now, please upload some files to initiate processing.");
    		return;
    	}
    }
    
    public boolean updateDatasetDetails(DatasetDetails dd)
    {
    	driver.FindElementById("editUploadSet").WaitUntilPresent().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEditDatasetPopup().Visible()  ;}}),Input.wait30);
    	if(!dd.getDatasetName().isEmpty())
    	{
    	getDatasetNameTxtBox().getTextarea().Clear();
    	getDatasetNameTxtBox().getTextarea().setText(dd.getDatasetName());
    	}
    	if(!dd.getCustodianName().isEmpty())
    	{
    	getCustodianNameTxtBox().getTextarea().Clear();
    	getCustodianNameTxtBox().getTextarea().setText(dd.getCustodianName());
    	}
    	if(!dd.getDescription().isEmpty())
    	{
    	getDescriptionTxtBox().getTextarea().Clear();
    	getDescriptionTxtBox().getTextarea().setText(dd.getDescription());
    	}
    	getDatasetPopupSaveBtn().Click();
    	getEditDatasetPopup().WaitWhilePresent();
    	return true;
    }
    
    public DatasetDetails getDatasetDetails()
    {
    	DatasetDetails dd = new DatasetDetails();
    	getDescriptionTxtBox().getTextarea().getText().trim();
    	dd.setDatasetName(getDatasetNameTxtBox().getTextarea().getText().trim());
    	dd.setCustodianName(getCustodianNameTxtBox().getTextarea().getText().trim());
    	dd.setDescription(getDescriptionTxtBox().getTextarea().getText().trim());
    	return dd;
    }
    
    public String[] getProjectPasswords()
    {
    	String[] projectpasswords;
    	driver.FindElementById("editProjectPwd").WaitUntilPresent().Click();
    	getProjectPwdPopup().WaitUntilPresent();
    	String passwords = getProjectPwdTxtBox().getTextarea().getText().trim();
    	projectpasswords = passwords.split("\\n");
    	return projectpasswords;    	
    }
    
    public int uploadFilesByFolder(String folderPath) throws InterruptedException
    {
    	System.out.println("*********Uplaoding Files started***********");
    	
    	int FileUploadCountExist = GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"));
    	String exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
		String fileListString = "";
		
    	//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getDropZoneLink().Visible() ;}}));
    	getDropZoneLink().Click();
    	exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
    	//String testDataFolder = System.getProperty("user.dir")+"\\sightline\\ICETestData";
    	File directory = new File(folderPath);
		File[] flist =  directory.listFiles();
		
		for (File file:flist)
		{
			if(file.isFile())
			{
				fileListString = fileListString+"\""+file.getName()+"\""+" ";
			}
		}
		Thread.sleep(2000);
		try {
			Runtime.getRuntime().exec(exeFile+"DragAndDrop_x64.exe"+" "+folderPath+"\\ "+fileListString);
		} catch (IOException e) {
			e.printStackTrace();	
		}
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getUploadFilesBtn().getText().contains("Uploading...") ;}}),Input.wait90);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"))== FileUploadCountExist+flist.length ;}}),100000);
    	
    	System.out.println("*********Uplaoding Files Completed***********");
    	return flist.length;
    }

    public void uploadFilesByFolderAndRefresh(String folderPath) throws InterruptedException 
    {
    	System.out.println("*********Uplaoding Files started***********");
    	
    	//int FileUploadCountExist = GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"));
    	String exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
		String fileListString = "";
		driver.waitForPageToBeReady();
    	//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getDropZoneLink().Visible() ;}}));
    	getDropZoneLink().Click();
    	exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
    	//String testDataFolder = System.getProperty("user.dir")+"\\sightline\\ICETestData";
    	File directory = new File(folderPath);
		File[] flist =  directory.listFiles();
		
		for (File file:flist)
		{
			if(file.isFile())
			{
				fileListString = fileListString+"\""+file.getName()+"\""+" ";
			}
		}
		Thread.sleep(2000);
		try {
			Runtime.getRuntime().exec(exeFile+"DragAndDrop_x64.exe"+" "+folderPath+"\\ "+fileListString);
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getUploadFilesBtn().getText().contains("Uploading...") ;}}),Input.wait90);
    	
		Thread.sleep(4000);
		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
		dp.getDatasetBtnLMenu().Click();
		}
    

    public void uploadFilesByFolderReturnWithoutCompleting(String folderPath) throws InterruptedException 
    {
    	System.out.println("*********Uplaoding Files started***********");
    	
    	//int FileUploadCountExist = GenFunc.StringToInt(driver.FindElementByCssSelector("#fileCount").GetAttribute("data-filecount"));
    	String exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
		String fileListString = "";
		driver.waitForPageToBeReady();
    	//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getDropZoneLink().Visible() ;}}));
    	getDropZoneLink().Click();
    	exeFile = System.getProperty("user.dir")+"\\src\\main\\java\\aIs\\";
    	//String testDataFolder = System.getProperty("user.dir")+"\\sightline\\ICETestData";
    	File directory = new File(folderPath);
		File[] flist =  directory.listFiles();
		
		for (File file:flist)
		{
			if(file.isFile())
			{
				fileListString = fileListString+"\""+file.getName()+"\""+" ";
			}
		}
		Thread.sleep(2000);
		try {
			Runtime.getRuntime().exec(exeFile+"DragAndDrop_x64.exe"+" "+folderPath+"\\ "+fileListString);
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getUploadFilesBtn().getText().contains("Uploading...") ;}}),Input.wait90);
    	
		//Thread.sleep(4000);
		}
    
    public void AcceptAlert()
    {
        Alert alert = driver.getWebDriver().switchTo().alert();
        System.out.println("Alert data: " + alert.getText());
        alert.accept();
        driver.waitForPageToBeReady();
    }
    
    public void AcceptNavigation()
    {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getConfirmNavigationPopup().Displayed();}}),Input.wait90);
    	
  		getConfirmNavigationPopupYesBtn().Click();
  		driver.waitForPageToBeReady();
    }

    
    public List<FileUploadDetails> getFileUploadedDetailsFromUITable()
    {
    	
    	List<FileUploadDetails> list = new ArrayList<FileUploadDetails>();
    	ElementCollection fileUploaded = driver.FindElementById("previews").getDivs();
    	for(Element fu:fileUploaded)
    	{
    		FileUploadDetails fud = new FileUploadDetails();
    		fud.setFileName(fu.FindElementByclassName("name").getText().toString().trim());
    		fud.setFileSize(fu.FindElementByclassName("size").getText().toString().trim());
    		fud.setFileUploadStatus(Float.parseFloat(fu.FindElementByclassName("progress-bar").GetAttribute("style").substring(7).split("%")[0].toString()));
    		fud.setError(fu.FindElementByclassName("error").getText().toString().trim());
    		fud.setFileElement(fu);
    		list.add(fud);
    	}
    	return list;
    }
    
    public boolean isFileUploadedByName(String fileName)
    {
    	List<FileUploadDetails> fud = getFileUploadedDetailsFromUITable();
    	Iterator<FileUploadDetails> it = fud.iterator();
    	for(;it.hasNext();)
    	{
    		FileUploadDetails fu= it.next();
    		if(fu.getFileName().equals(fileName))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public void deleteUploadFileByName(String fileName)
    {
    	if(isFileUploadedByName(fileName))
    	{
    		ElementCollection fileUploaded = driver.FindElementById("previews").getDivs();
        	for(Element fu:fileUploaded)
        	{
        		if(fu.FindElementByclassName("name").getText().toString().trim().equalsIgnoreCase(fileName))
        		{
        			fu.FindElementByclassName("dz-remove").Click();
        			driver.waitForPageToBeReady();
        		}
        	}
    	}else
    	{
    		new Exception("The file name provided is not uploaded to delete.");
    		return;
    	}
    	
    }
}

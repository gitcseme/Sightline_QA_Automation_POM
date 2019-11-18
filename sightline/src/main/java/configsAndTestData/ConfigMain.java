package configsAndTestData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public class ConfigMain {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public String env;
	public String newProject;
	public String ingestion;
	
	public String browserName;
	public String screenShotOnPass;
	public String screenShotOnFail;
	public String batchFilesPath;
	public String suite;
	
	public int wait30;
	public int wait60;
	public int wait90;
	public int wait120;
	public int interval;
	public String  Collection1KFolder;
	public String  FamilyFolder;
	public String  AllSourcesFolder;
	public String  Collection1KDATFile;
	public String  FamilyDATFile;
	public String  AllSourcesDATFile;
	public String  Collection1KDockey;
	public String  FamilyDockey;
	public String  AllSourcesDockey;
	public String  Collection1KSourceDatField2;
	public String  FamilySourceDatField2;
	public String  AllSourcesSourceDatField2;
	public String Collection1KTextFile;
	public String FamilyTextFile;
	public String AllSourcesTextFile;
	public String FamilyNativeFile;
	public String AllSourcesNativeFile;
	public int numberOfDataSets;
	
	public int getNumberOfDataSets() {
		return numberOfDataSets;
	}
	public void setNumberOfDataSets(int numberOfDataSets) {
		this.numberOfDataSets = numberOfDataSets;
	}
	public String getCollection1KTextFile() {
		return Collection1KTextFile;
	}
	public void setCollection1KTextFile(String collection1kTextFile) {
		this.Collection1KTextFile = collection1kTextFile;
	}
	public String getFamilyTextFile() {
		return FamilyTextFile;
	}
	public void setFamilyTextFile(String familyTextFile) {
		this.FamilyTextFile = familyTextFile;
	}
	public String getAllSourcesTextFile() {
		return AllSourcesTextFile;
	}
	public void setAllSourcesTextFile(String allSourcesTextFile) {
		this.AllSourcesTextFile = allSourcesTextFile;
	}
	public String getFamilyNativeFile() {
		return FamilyNativeFile;
	}
	public void setFamilyNativeFile(String familyNativeFile) {
		this.FamilyNativeFile = familyNativeFile;
	}
	public String getAllSourcesNativeFile() {
		return AllSourcesNativeFile;
	}
	public void setAllSourcesNativeFile(String allSourcesNativeFile) {
		this.AllSourcesNativeFile = allSourcesNativeFile;
	}
	
	public String getFamilyFolder() {
		return FamilyFolder;
	}
	public void setFamilyFolder(String familyFolder) {
		this.FamilyFolder = familyFolder;
	}
	public String getAllSourcesFolder() {
		return AllSourcesFolder;
	}
	public void setAllSourcesFolder(String allSourcesFolder) {
		this.AllSourcesFolder = allSourcesFolder;
	}
	public String getCollection1KDATFile() {
		return Collection1KDATFile;
	}
	public void setCollection1KDATFile(String collection1kdatFile) {
		this.Collection1KDATFile = collection1kdatFile;
	}
	public String getFamilyDATFile() {
		return FamilyDATFile;
	}
	public void setFamilyDATFile(String familyDATFile) {
		this.FamilyDATFile = familyDATFile;
	}
	public String getAllSourcesDATFile() {
		return AllSourcesDATFile;
	}
	public void setAllSourcesDATFile(String allSourcesDATFile) {
		this.AllSourcesDATFile = allSourcesDATFile;
	}
	public String getCollection1KDockey() {
		return Collection1KDockey;
	}
	public void setCollection1KDockey(String collection1kDockey) {
		this.Collection1KDockey = collection1kDockey;
	}
	public String getFamilyDockey() {
		return FamilyDockey;
	}
	public void setFamilyDockey(String familyDockey) {
		this.FamilyDockey = familyDockey;
	}
	public String getAllSourcesDockey() {
		return AllSourcesDockey;
	}
	public void setAllSourcesDockey(String allSourcesDockey) {
		this.AllSourcesDockey = allSourcesDockey;
	}
	public String getCollection1KSourceDatField2() {
		return Collection1KSourceDatField2;
	}
	public void setCollection1KSourceDatField2(String collection1kSourceDatField2) {
		this.Collection1KSourceDatField2 = collection1kSourceDatField2;
	}
	public String getFamilySourceDatField2() {
		return FamilySourceDatField2;
	}
	public void setFamilySourceDatField2(String familySourceDatField2) {
		this.FamilySourceDatField2 = familySourceDatField2;
	}
	public String getAllSourcesSourceDatField2() {
		return AllSourcesSourceDatField2;
	}
	public void setAllSourcesSourceDatField2(String allSourcesSourceDatField2) {
		this.AllSourcesSourceDatField2 = allSourcesSourceDatField2;
	}
	public  String getSuite() {
		return suite;
	}
	public  void setSuite(String suite) {
		this.suite = suite;
	}
	public String getNewProject() {
		return newProject;
	}
	public void setNewProject(String newProject) {
		this.newProject = newProject;
	}
	
	public  String getScreenShotOnPass() {
		return screenShotOnPass;
	}
	public  void setScreenShotOnPass(String screenShotOnPass) {
		this.screenShotOnPass = screenShotOnPass;
	}
	public  String getScreenShotOnFail() {
		return screenShotOnFail;
	}
	public void setScreenShotOnFail(String screenShotOnFail) {
		this.screenShotOnFail = screenShotOnFail;
	}
	public  String getBatchFilesPath() {
		return batchFilesPath;
	}
	public  void setBatchFilesPath(String batchFilesPath) {
		this.batchFilesPath = batchFilesPath;
	}
	public  int getInterval() {
		return interval;
	}
	public  void setInterval(int interval) {
		this.interval = interval;
	}
	public int getWait30() {
		return wait30;
	}
	public void setWait30(int wait30) {
		this.wait30 = wait30;
	}
	public int getWait60() {
		return wait60;
	}
	public void setWait60(int wait60) {
		this.wait60 = wait60;
	}
	public int getWait90() {
		return wait90;
	}
	public void setWait90(int wait90) {
		this.wait90 = wait90;
	}
	public int getWait120() {
		return wait120;
	}
	public void setWait120(int wait120) {
		this.wait120 = wait120;
	}
	
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	
	
	}

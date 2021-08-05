package configsAndTestData;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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
	public int wait3;
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
	public boolean ExtentReportMethodWise;	
	public boolean LogJiraTicket;
	public String JiraUrl;
	public String JiraUserName;
	public String JiraToken;
	public String JiraProject;
	public String TestingBuild;
	public String ICESmokeFolderPath;
}


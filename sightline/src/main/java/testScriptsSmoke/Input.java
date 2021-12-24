package testScriptsSmoke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;
import configsAndTestData.Environment;
import configsAndTestData.TestData;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass; 
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class Input {
	Driver driver;
	LoginPage lp;

	BaseClass bc;
	// Config and test data files---------------------------------//
	public static ConfigMain config;
	public static Environment envConfig;

	public static TestData testData;
	// ConfigMain data---------------------------------------------
	public static String newProject;
	public static String ingestion;
	public static String suite;
	public static String browserName;
	public static String screenShotOnPass;
	public static String screenShotOnFail;
	public static int wait3;
	public static int wait30;
	public static int wait60;
	public static int wait90;
	public static int wait120;
	public static int interval;
	public static String batchFilesPath;
	public static int numberOfDataSets;
	public static boolean extentReportMethodWise;
	public static boolean logJiraTicket;
	public static String jiraUrl;
	public static String jiraUserName;
	public static String jiraToken;
	public static String jiraProject;
	public static String testingBuild;
	public static String iCESmokeFolderPath;
	public static String ICEProjectName;

	// Environment data---------------------------------------------

	public static String url;
	public static String prodPath;
	public static String projectName;
	public static String sa1password;
	public static String sa1userName;
	public static String da1password;
	public static String da1userName;
	public static String pa1userName;
	public static String pa1password;
	public static String rmu1userName;
	public static String rmu1password;
	public static String rev1userName;
	public static String rev1password;
	public static String shareTo;
	public static String pa1FullName;
	public static String rmu1FullName;
	public static String rev1FullName;
	public static String pa2FullName;
	public static String rmu2FullName;
	public static String rev2FullName;
	public static String pa2userName;
	public static String pa2password;
	public static String rmu2userName;
	public static String rmu2password;
	public static String rev2userName;
	public static String rev2password;
	public static String domainName;

	// Test data------------------------------------------------------
	public static String searchString1;
	public static String searchString2;
	public static int pureHitSeachString1;
	public static int pureHitSeachString2;
	public static int searchString1ANDsearchString2;
	public static int searchString1ORsearchString2;
	public static int searchString1NOTsearchString2;
	public static int searchString2NOTsearchString1;
	public static String audioSearchString1;
	public static int audioSearchString1pureHit;
	public static String conceptualSearchString1;
	public static int conceptualSearchString1PureHit;
	public static String metaDataCN;
	public static int metaDataCNcount;
	public static String Collection1KFolder;
	public static String FamilyFolder;
	public static String AllSourcesFolder;
	public static String Collection1KDATFile;
	public static String FamilyDATFile;
	public static String AllSourcesDATFile;
	public static String Collection1KDockey;
	public static String FamilyDockey;
	public static String AllSourcesDockey;
	public static String Collection1KSourceDatField2;
	public static String FamilySourceDatField2;
	public static String AllSourcesSourceDatField2;
	public static String Collection1KTextFile;
	public static String FamilyTextFile;
	public static String AllSourcesTextFile;
	public static String FamilyNativeFile;
	public static String AllSourcesNativeFile;
	public static String SourceLocation;
	public static int totalNumberOfDocs;
	public static int totalNumberOfDocsincludeadvoption;
	public static String MasterPDF1location;
	public static String MasterPDF2location;

	// Docview Analytics Mohan Indium- Modified 8/24/21
	public static String Panel;
	public static String AssgnName;
	public static String SavedSearch;
	public static String TextValue;
	public static int pureHitCount;
	public static String TextEmpty;
	public static String docName;
	public static int pureHitCount1;
	public static String MiniDocId;
	public static String ThreadQuery;
	public static String NewDocId;
	public static String MetaDataId;
	public static String documentToBeScrolled;
	public static String conceptualDocument;
	public static String conceptualDocumentReviewer;
	public static String threadDocId;
	public static String principalDocId;
	public static String familyDocumentForReviewer;

	public static String principalDocId1;
	public static String principalDocId2;
	public static String principalDocId3;
	public static String principalDocId4;
	public static String principalDocId5;
	public static String principalDocId6;
	public static String sourceDocId;
	public static String sourceDocId1;
	public static String sourceDocId2;
	public static String sourceDocId3;
	public static String sourceDocId4;
	public static String sourceDocId5;
	public static String nearDupeCompletedDocId;
	public static String nearDupeDocument;
	public static String familyDocument;
	public static String nearDupeDocumentForReviewer;
	public static String threadDocumentForReviewer;
	public static String nearDupeDocId;
	public static String nearDupeCompletedDocIdReviewer;
	public static String historyClockIconDocId;
	public static String newNearDupeDocId;
	public static String warningDocId;
	public static String nearDupeBulkAssign;
	public static String nearDupeBulkAssignId;
	public static String nearDupeBlukAssignReviewer;
	public static String nearDupeBulkAssignReviewId;

	// DocView codingform Baskar Indium

	public static String savedName;
	public static String codingFormName;
	public static String assignmentSelection;
	public static String stampColour;
	public static String assignmentLevelStampOFF;
	public static String searchText;
	public static String reviewerName;
	public static String stampColours;
	public static String stampSelection;

	// Baskar Indium Audio Search to Docview MiniList
	public static String language;
	public static int documentIdNum;
	public static String searchStringStar;
	public static String FolderName;
	public static String docIDs;

	// added by sowndariya
	public static String searchString3;
	public static String searchString4;
	public static String testData1;
	public static String tagNamePrev;
	public static String tagNameTechnical;
	public static String documentID;
	public static String orphanDocument;
	public static String signatureDocumentID;
	public static String searchString20Docs;
	public static String prodPathUAT;
	public static String bates;
	public static String batesNumber;
	public static String ChildBates;
	public static String Production;
	public static String TIFFPageCount;
	


	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 */
	public static String historyTab;
	public static String documentConceptualTab;
	public static String randomText;
	public static String metaDataName;
	public static String is_Or_Range;
	public static String second_Input;
	public static String defaultRedactionTag;
	public static String fldClassification;
	public static String fldDescription;
	public static String fieldType;
	public static String fieldLength;
	public static String codeFormName;
	public static String securityGroup;

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 */

	public static String actionColumnName;
	public static String nameColumnName;
	public static String userColumnName;
	public static String timeStampColumnName;

	// Added by krishna
	public static String pageNumber;
	public static String searchDocId;
	
	public static String keyWordHexCode;
	public static String docIdKeyWordTest;
	public static String docIdThumbnails;

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 */
	public static String betweenTagName;

	public static String searchBulletDocId;
	public static String searchTestSG;
	public static String duplicateDocId;
	public static String pageRange;
	public static String fullPageRange;
	public static String iconColor;

	// Added by Raghuram - Modified 10/27/21
	public static String docHighlightColor;
	public static String sortDataBy;
	public static String sortType;
	public static String fileDownloadLocation;
	public static String completedWithErrColorCodeBR;
	public static String shareSearchPA;
	public static String shareSearchDefaultSG;
	public static String searchString5;
	public static String searchString6;
	public static String mySavedSearch;
	public static String zeroPureHitString;
	public static String invalidBatchFileNewLocation;
	public static String xlsBatchFile;
	public static String invalidBatchFileFormatErrMsg;
	public static String metaDataCustodianNameInput;
	public static String selectionHighlightColor;
	public static String searchString7;

	// Added BY Jeevitha
	public static String colorCodeOfRed;
	public static String crammerdocumentID;
	public static String batchFileNewLocation;
	public static String performaceBatchFile;
	public static String bullHornIconColor;
	public static String progresBarColor;

	// Added by gopinath - 25/10/2021
	public static String audioSearch;
	public static String audioLanguage;

	/**
	 * @author : Gopinath Created date: 03-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 */

	public static String orderCriteria;
	public static String orderType;

	// Added by Gopinath - 06/10/2021
	public static String docFileType;

	public static String docIdRemarks;

	// Added by Gopinath - 08-09-2021
	public static String savedPageTitle;

	// Added by Gopinath - 17-09-2021
	public static String comment;

	// Added by gopinath - 21/09/2021
	public static String codeSameAsLastMsg;

	// Added by Gopinath - 23/09/2021
	public static String startTime;
	public static String endTime;

	// Added by Jagadeesh.jana - 24/09/2021
	public static String colorCodeHighlight;

	// Added by Gopinath - 27/09/2021
	public static String pageLength;

	// Added by Gopinath - 30/09/2021
	public static String date;

	// Added by Gopinath - 05/10/2021
	public static String docFileSize;
	public static String docFileName;

	public static String docIdRemarks2;

	// Added by Gopinath- 11/10/2021
	public static String atternoyClient;
	public static String confidential;
	public static String reviewed;
	public static String documentComments;

	// Added by Gopinath - 27/10/2021
	public static String excelFileDocument;
	public static String mp3FileDocument;

	// Added by Gopinath- 01/11/2021
	public static String fileExtDiffDocument;
	public static String fileExtBlankDocument;
	public static String translationDocument;

	public static String WPbatchFile;
	
	//Added by Gopinath - 16/11/2021
	public static String signDocumentId;
	public static String technicalIssue;
	public static String crammerDocId;
	public static String produced;
	public static String producedDeleted;
	
	public static String EmailAuthourName;
	public static String  MetaDataEAName;
	public static String  MetaDataDomainName;
	public static String  MetaDataReciepientsDomain;
	public static String KeyWordColour;
	//Added by Gopinath - 22/11/2021
	public static String batesDocumentId;
	
//	Added by baskar
	public static String tinyInt;
	public static String smallInt;
	public static String averageInt;
	public static String bigInt;
	public static String hugeInt;
	public static String docBasic;
	
	//Added by Gopinath - 25/11/2021
	public static String exText;
	
	//Added by Gopinath - 29/11/2021
	public static String paginationDocId;
//	Added by Baskar - 29/11/2021
	public static String hidden;
	public static String notSelectable;
	public static String optional;
	public static String required;
	public static String tag;
	public static String comments;
	public static String metaData;
	public static String checkItem;
	public static String staticText;
	public static String radioGroup;
	public static String checkGroup;
	public static String yesButton;
	
	//added by Aathith
	public static String pageCount;
	public static String errorMsg;
	public static String helpText;
	public static String thisHidden;
	public static String thisReadOnly;
	public static String thisOptional;
	public static String thisRequired;
	public static String select;
	public static String notSelect;
	

	// Added by Brundha-1/12/2021
	public static String documentId;
	
	 

	//Added by Gopinath- 30/11/2021
	public static String postGenQcChecks;

	
	//Added by Gopinath - 03/12/2021
	public static String thankyouText;
	
	//Added by gopinath - 08/12/2021
	public static String anotherRemarkMessage;
	
	//Added by gopinath - 09/12/2021
	public static String familyMembersDocId;
	
	//Added by Gopinath - 15/Dec/2021
	public static String proximity;
	public static String masterDate;
	
	//Added by gopinath  - 16/Dec/2021
	public static String productionText;
	public static String tiffPageCountNam;
	public static String tiffPageCountText;
	
	@BeforeSuite(alwaysRun = true)

	public void loadEnvConfig() throws ParseException, InterruptedException, IOException {

		System.out.println("*****************************************************");
		UtilityLog.info("*****************************************************");
		// Common Data-------------------------------------------------------------
		config = (ConfigMain) new ConfigLoader().load("ConfigMain");
		// Reading from Config Main File
		envConfig = (Environment) new ConfigLoader().load(config.getEnv());
		// Reading from Jenkins
		// envConfig = (Environment) new
		// ConfigLoader().load(System.getProperty("tEnvironment"));
		System.out.println("Running scripts on " + config.getEnv() + " Environment");
		UtilityLog.info("Running scripts on " + config.getEnv() + " Environment");

		newProject = config.getNewProject();
		ingestion = config.getIngestion();
		suite = config.getSuite();
		numberOfDataSets = config.getNumberOfDataSets();
		browserName = config.getBrowserName();
		screenShotOnPass = config.getScreenShotOnPass();
		screenShotOnFail = config.getScreenShotOnFail();
		batchFilesPath = config.getBatchFilesPath();
		wait3 = config.getWait3();
		wait30 = config.getWait30();
		wait60 = config.getWait60();
		wait90 = config.getWait90();
		wait120 = config.getWait120();
		interval = config.getInterval();
		extentReportMethodWise = config.isExtentReportMethodWise();
		logJiraTicket = config.isLogJiraTicket();
		jiraUrl = config.getJiraUrl();
		jiraUserName = config.getJiraUserName();
		jiraToken = config.getJiraToken();
		jiraProject = config.getJiraProject();
		testingBuild = config.getTestingBuild();
		iCESmokeFolderPath = config.getICESmokeFolderPath();

		/*
		 * Ingestion Data
		 */
		Collection1KFolder = config.getCollection1KFolder();
		FamilyFolder = config.getFamilyFolder();
		AllSourcesFolder = config.getAllSourcesFolder();
		Collection1KDATFile = config.getCollection1KDATFile();
		FamilyDATFile = config.getFamilyDATFile();
		AllSourcesDATFile = config.getAllSourcesDATFile();
		Collection1KDockey = config.getCollection1KDockey();
		FamilyDockey = config.getFamilyDockey();
		AllSourcesDockey = config.getAllSourcesDockey();
		Collection1KSourceDatField2 = config.getCollection1KSourceDatField2();
		FamilySourceDatField2 = config.getFamilySourceDatField2();
		AllSourcesSourceDatField2 = config.getAllSourcesSourceDatField2();
		Collection1KTextFile = config.getCollection1KTextFile();
		FamilyTextFile = config.getFamilyTextFile();
		AllSourcesTextFile = config.getAllSourcesTextFile();
		FamilyNativeFile = config.getFamilyNativeFile();
		AllSourcesNativeFile = config.getAllSourcesNativeFile();

		// Environment data-------------------------------------------------------------
		url = envConfig.getUrl();
		projectName = envConfig.getProjectName();
		domainName = envConfig.getDomainName();
		sa1userName = envConfig.getSa1userName();
		sa1password = envConfig.getSa1password();
		pa1userName = envConfig.getPa1userName();
		pa1password = envConfig.getPa1password();
		rmu1userName = envConfig.getRmu1userName();
		rmu1password = envConfig.getRmu1password();
		rev1userName = envConfig.getRev1userName();
		rev1password = envConfig.getRev1password();
		pa2userName = envConfig.getPa2userName();
		pa2password = envConfig.getPa2password();
		rmu2userName = envConfig.getRmu2userName();
		rmu2password = envConfig.getRmu2password();
		rev2userName = envConfig.getRev2userName();
		rev2password = envConfig.getRev2password();
		shareTo = envConfig.shareTo;
		pa1FullName = envConfig.getPa1FullName();
		rmu1FullName = envConfig.getRmu1FullName();
		rev1FullName = envConfig.getRev1FullName();
		pa2FullName = envConfig.getPa2FullName();
		rmu2FullName = envConfig.getRmu2FullName();
		rev2FullName = envConfig.getRev2FullName();
		prodPath = envConfig.getProdpath();
		SourceLocation = envConfig.getSourceLocation();
		ICEProjectName = envConfig.getICEProjectName();
		da1userName = envConfig.getDa1userName();
		da1password = envConfig.getDa1password();
		// Test data-------------------------------------------------------------

		loadSuiteTestData();// Load required suite data first, smoke or regression one - Modified date :
							// 8/24/21
		testData = (TestData) new ConfigLoader().load("TestData");

		searchString1 = testData.getSearchString1();
		searchString2 = testData.getSearchString2();
		audioSearchString1 = testData.getAudioSearchString1();

		// Baskar Indium Audio Search to Docview MiniList - Modified date : 9/01/21
		language = testData.getLanguage();
		documentIdNum = testData.getDocumentIdNum();
		searchStringStar = testData.getSearchStringStar();
		docIDs = testData.getDocIDs();

		// Docview Analytics Mohan Indium
		Panel = testData.getPanel();
		AssgnName = testData.getAssgnName();
		SavedSearch = testData.getSavedSearch();
		TextValue = testData.getTextValue();
		TextEmpty = testData.getTextEmpty();
		pureHitCount = testData.getPureHitCount();
		FolderName = testData.getFolderName();
		docName = testData.getDocName();
		pureHitCount1 = testData.getPureHitCount1();
		MiniDocId = testData.getMiniDocId();
		ThreadQuery = testData.getThreadQuery();
		NewDocId = testData.getNewDocId();
		MetaDataId = testData.getMetaDataId();
		documentToBeScrolled = testData.getDocumentToBeScrolled();
		conceptualDocument = testData.getConceptualDocument();
		conceptualDocumentReviewer = testData.getConceptualDocumentReviewer();
		familyDocument = testData.getFamilyDocument();
		threadDocId = testData.getThreadDocId();
		principalDocId = testData.getPrincipalDocId();
		principalDocId1 = testData.getPrincipalDocId1();
		principalDocId2 = testData.getPrincipalDocId2();
		principalDocId3 = testData.getPrincipalDocId3();
		principalDocId4 = testData.getPrincipalDocId4();
		principalDocId5 = testData.getPrincipalDocId5();
		principalDocId6 = testData.getPrincipalDocId6();
		sourceDocId = testData.getSourceDocId();
		sourceDocId1 = testData.getSourceDocId1();
		sourceDocId2 = testData.getSourceDocId2();
		sourceDocId3 = testData.getSourceDocId3();
		sourceDocId4 = testData.getSourceDocId4();
		sourceDocId5 = testData.getSourceDocId5();
		nearDupeDocument = testData.getNearDupeDocument();
		nearDupeDocumentForReviewer = testData.getNearDupeDocumentForReviewer();
		threadDocumentForReviewer = testData.getThreadDocumentForReviewer();
		newNearDupeDocId = testData.getNewNearDupeDocId();

		nearDupeCompletedDocId = testData.getNearDupeCompletedDocId();
		nearDupeDocId = testData.getNearDupeDocId();
		nearDupeCompletedDocIdReviewer = testData.getNearDupeCompletedDocIdReviewer();
		familyDocumentForReviewer = testData.getFamilyDocumentForReviewer();
		historyClockIconDocId = testData.getHistoryClockIconDocId();
		warningDocId=testData.getWarningDocId();
		nearDupeBulkAssign=testData.getNearDupeBulkAssign();
		nearDupeBulkAssignId=testData.getNearDupeBulkAssignId();
		nearDupeBlukAssignReviewer=testData.getNearDupeBulkAssignReviewer();
		nearDupeBulkAssignReviewId=testData.getNearDupeBulkAssignReviewId();

		// Docview Coding Form Baskar Indium
		savedName = testData.getSavedName();
		codingFormName = testData.getCodingFormName();
		assignmentSelection = testData.getAssignmentSelection();
		stampColour = testData.getStampColour();
		assignmentLevelStampOFF = testData.getAssignmentLevelStampOFF();
		searchText = testData.getSearchText();
		reviewerName = testData.getReviewerName();
		stampColours = testData.getStampColours();
		stampSelection = testData.getStampSelection();

		// Indium Sowndarya
		testData1 = testData.getTestData1();
		tagNamePrev = testData.getTagNamePrev();
		tagNameTechnical = testData.getTagNameTechnical();
		 documentID=testData.getDocumentID();
		orphanDocument = testData.getOrphanDocument();
		searchString3 = testData.getSearchString3();
		searchString4 = testData.getSearchString4();
		signatureDocumentID=testData.getSignatureDocumentID();
		searchString20Docs=testData.getSearchString20Docs();
		prodPathUAT=testData.getProdPathUAT();
		bates=testData.getBates();
		batesNumber=testData.getBatesNumber();
		ChildBates=testData.getChildBates();
		Production=testData.getProduced();
		TIFFPageCount=testData.getPageCount();
		
		
		
		/**
		 *@author Aathith.Senthilkumar 
		 */
		pageCount=testData.getPageCount();
		errorMsg=testData.getErrorMsg();
		helpText=testData.getHelpText();
		thisHidden=testData.getThisHidden();
		thisReadOnly=testData.getThisReadOnly();
		thisOptional=testData.getThisOptional();
		thisRequired=testData.getThisRequired();
		select=testData.getSelect();
		notSelect=testData.getNotSelect();
		
		
		/**
		 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
		 */
		historyTab = testData.getHistoryTab();
		documentConceptualTab = testData.getDocumentConceptualTab();
		randomText = testData.getRandomText();
		metaDataName = testData.getMetaDataName();
		is_Or_Range = testData.getIs_Or_Range();
		second_Input = testData.getSecond_Input();
		defaultRedactionTag = testData.getDefaultRedactionTag();
		fldClassification = testData.getFldClassification();
		fldDescription = testData.getFldDescription();
		fieldType = testData.getFieldType();
		fieldLength = testData.getFieldLength();
		codeFormName = testData.getCodeFormName();
		securityGroup = testData.getSecurityGroup();
		actionColumnName = testData.getActionColumnName();
		nameColumnName = testData.getNameColumnName();
		userColumnName = testData.getUserColumnName();
		timeStampColumnName = testData.getTimeStampColumnName();

		// Added by Krishna
		pageNumber = testData.getPageNumber();
		searchDocId = testData.getSearchDocId();
		searchBulletDocId = testData.getSearchBulletDocId();
		searchTestSG = testData.getSearchTestSG();
		duplicateDocId = testData.getDuplicateDocId();
		pageRange = testData.getPageRange();
		fullPageRange = testData.getFullPageRange();
		iconColor = testData.getIconColor();
		docIdThumbnails = testData.getDocIdThumbnails();

		// Added by Raghuram
		docHighlightColor = testData.getDocHighlightColor();
		sortDataBy = testData.getSortDataBy();
		sortType = testData.getSortType();
		fileDownloadLocation = testData.getFileDownloadLocation();
		completedWithErrColorCodeBR = testData.getCompletedWithErrColorCodeBR();
		shareSearchPA = testData.getShareSearchPA();
		shareSearchDefaultSG = testData.getShareSearchDefaultSG();
		searchString5 = testData.getSearchString5();
		searchString6 = testData.getSearchString6();
		mySavedSearch = testData.getMySavedSearch();
		zeroPureHitString = testData.getZeroPureHitString();
		invalidBatchFileNewLocation = testData.getInvalidBatchFileNewLocation();
		xlsBatchFile = testData.getXlsBatchFile();
		invalidBatchFileFormatErrMsg = testData.getInvalidBatchFileFormatErrMsg();
		metaDataCustodianNameInput = testData.getMetaDataCustodianNameInput();
		selectionHighlightColor = testData.getSelectionHighlightColor();
		searchString7 = testData.getSearchString7();

		// Added by Jeevitha
		colorCodeOfRed = testData.getColorCodeOfRed();
		metaDataCN = testData.getMetaDataCN();
		batchFileNewLocation = testData.getBatchFileNewLocation();
		bullHornIconColor = testData.getBullHornIconColor();
		progresBarColor = testData.getProgresBarColor();

		/**
		 * @author : Gopinath Created date: 01-09-2021 Modified date: NA Modified
		 *         by:Gopinath.
		 */
		betweenTagName = testData.betweenTagName;

		orderCriteria = testData.orderCriteria;
		orderType = testData.orderType;

		savedPageTitle = testData.savedPageTitle;

		// Added by gopinath 17-09-2021
		comment = testData.comment;

		// Added by Gopinath - 21/09/2021
		codeSameAsLastMsg = testData.codeSameAsLastMsg;

		// Added by Gopinath - 23/09/2021
		startTime = testData.startTime;
		endTime = testData.endTime;

		// Added by Jagadeesh.jana
		colorCodeHighlight = testData.colorCodeHighlight;

		// Added by Jeevitha
		crammerdocumentID = testData.getCrammerdocumentID();

		/**
		 * @author : Gopinath Created date: NA Modified date: 23-08-2021 Modified
		 *         by:Gopinath.
		 */
		metaDataCN = testData.getMetaDataCN();

		/**
		 * @author : Gopinath Created date: 01-09-2021 Modified date: NA Modified
		 *         by:Gopinath.
		 */
		betweenTagName = testData.betweenTagName;

		orderCriteria = testData.orderCriteria;
		orderType = testData.orderType;

		savedPageTitle = testData.savedPageTitle;

		// Added by gopinath 17-09-2021
		comment = testData.comment;

		// Added by Gopinath - 21/09/2021
		codeSameAsLastMsg = testData.codeSameAsLastMsg;

		// Added by Gopinath - 23/09/2021
		startTime = testData.startTime;
		endTime = testData.endTime;

		// Added by Jagadeesh.jana
		colorCodeHighlight = testData.colorCodeHighlight;

		// Added by Gopinath- 27/09/2021
		pageLength = testData.pageLength;

		// Added by Gopinath- 30/09/2021
		date = testData.date;

		// Added by Gopinath -05/10/2021
		docFileSize = testData.docFileSize;
		docFileName = testData.docFileName;
		reviewed = testData.reviewed;
		documentComments = testData.documentComments;

		// Added by Gopinath - 06/10/2021
		docFileType = testData.docFileType;

		docIdRemarks = testData.docIdRemarks;
		docIdRemarks2 = testData.docIdRemarks2;

		// Added by Gopinath - 11-10-2021
		atternoyClient = testData.atternoyClient;
		confidential = testData.atternoyClient;

		// Added by Gopinath
		audioSearch = testData.audioSearch;
		audioLanguage = testData.audioLanguage;

		// Added by gopinath - 27/10/2021
		excelFileDocument = testData.excelFileDocument;
		mp3FileDocument = testData.mp3FileDocument;

		// Added by gopinath - 11/02/2021
		fileExtDiffDocument = testData.fileExtDiffDocument;
		fileExtBlankDocument = testData.fileExtBlankDocument;
		translationDocument = testData.translationDocument;

		

		//Added 
		WPbatchFile=testData.WPbatchFile;
		
		//Added by Iyappan
	    WPbatchFile = testData.getWPbatchFile();
	    
	    // Added by Krishna
	    docIdKeyWordTest = testData.getDocIdKeyWordTest();
	    keyWordHexCode = testData.getKeyWordHexCode();


	  //Added by Gopinath - 16/11/2021
		signDocumentId = testData.getSignDocumentId();
		technicalIssue = testData.getTechnicalIssue();
		crammerDocId = testData.getCrammerDocId();
		produced = testData.getProduced();
		producedDeleted = testData.getProducedDeleted();
		//added by jayanthi
		EmailAuthourName=testData.getEmailAuthourName();
	     MetaDataEAName=testData.getMetaDataEAName();
	       MetaDataDomainName=testData.getMetaDataDomainName();
	 	  MetaDataReciepientsDomain=testData.getMetaDataReciepientsDomain();
	 	 KeyWordColour=testData.getKeyWordColour();
           //Added By jeevitha
	    performaceBatchFile =testData.getPerformaceBatchFile();

	    
	    //Added by gopinath-22/11/2021
	    batesDocumentId = testData.getProducedDeleted();
	    
//	    Added by Baskar 
	    tinyInt=testData.getTinyInt();
	    smallInt=testData.getSmallInt();
	    averageInt=testData.getAverageInt();
	    bigInt=testData.getBigInt();
	    hugeInt=testData.getHugeInt();
	    docBasic=testData.getDocBasic();
	    
	    
	    //Added by Gopianth - 25/11/2021
	    exText = testData.exText;
	    
	    //Added by Gopinath - 29/11/2021
	    paginationDocId = testData.paginationDocId;
//	    Added by Baskar -29/11/2021
	    hidden=testData.getHidden();
	    optional=testData.getOptional();
	    notSelectable=testData.getNotSelectable();
	    required=testData.getRequired();
	    tag=testData.getTag();
	    comments=testData.getComments();
	    metaData=testData.getMetaData();
	    checkItem=testData.getCheckItem();
	    staticText=testData.getStaticText();
	    radioGroup=testData.getRadioGroup();
	    checkGroup=testData.getCheckGroup();
	    yesButton=testData.getYesButton();
	    

	    // Added by Brundha-1/12/2021  
	 
	   documentId=testData.getDocumentID(); 

	  //Added by Gopinath - 30/11/2021
	    postGenQcChecks = testData.getPostGenQcChecks();
	    
	    //Added by Gopinath - 03/12/2021
	    thankyouText = testData.getThankyouText();
	    
	  //Added by Gopinath - 08/12/2021
	    anotherRemarkMessage = testData.getAnotherRemarkMessage();
	    
	    //Added by Gopinath - 09/12/2021
	    familyMembersDocId = testData.getFamilyMembersDocId();

	    
	    //Added by Gopinath - 15/Dec/2021
	    proximity= testData.getProximity();
		masterDate = testData.getMasterDate();
		
		//Added by Gopinath - 16/Dec/2021
		productionText = testData.getProductionText();
		tiffPageCountNam = testData.getTiffPageCountNam();
		tiffPageCountText = testData.getTiffPageCountText();

		System.out.println("*****************************************************");
		UtilityLog.info("*****************************************************");

		// createproject if configured
		projectCreationAndUserAssignment();

		// do ingestion if configured
		ingestion();

		// BulkRelase all docs
		// releaseDocs(); 

	}

	public void projectCreationAndUserAssignment() throws ParseException, InterruptedException, IOException {

		if (newProject.equalsIgnoreCase("yes")) {
			driver = new Driver();
			System.out.println("******Creating new project********");
			UtilityLog.info("******Creating new project********");
			// clean browsers---------------------------------------------------------
			System.out.println("Clearing browser cache, Please wait.......");
			UtilityLog.info("Clearing browser cache, Please wait.......");
			LoginPage.clearBrowserCache();

			lp = new LoginPage(driver);

			lp.loginToSightLine(sa1userName, sa1password);
			String hcode = "HC" + Utility.dynamicNameAppender();
			ProjectPage page = new ProjectPage(driver);
			page.AddNonDomainProject(projectName, hcode);

			ManageUsersPage userpage = new ManageUsersPage(driver);
			userpage.AddUserstoProject(projectName);

			userpage.BulkUserAccess(Input.projectName);
			/*
			 * System.out.println("add rights manually"); Thread.sleep(40000);
			 */

			lp.logout();

			System.out.println("******Project creation and user assignment is done********\n");
			UtilityLog.info("******Project creation and user assignment is done********\n");

		} else {
			System.out.println("Running on exsiting project : " + Input.projectName);
			UtilityLog.info("Running on exsiting project : " + Input.projectName);
		}
	}

	public void ingestion() throws InterruptedException {
		try {
			if (ingestion.equalsIgnoreCase("yes")) {
				driver = new Driver();
				lp = new LoginPage(driver);
				ArrayList<String> dataset = new ArrayList<String>();
				if (Input.suite.equalsIgnoreCase("Regression")) {
					dataset.add("Automation_Collection1K_Tally");
					dataset.add("Automation_AllSources");
					dataset.add("Automation_20Family_20Threaded");
				} else if (Input.suite.equalsIgnoreCase("smoke")) {
					dataset.add("Automation_AllSources");
				}
				// System.out.println(dataset);
				UtilityLog.info(dataset);
				System.out.println("******Ingestion will start for " + dataset + "********");
				UtilityLog.info("******Ingestion will start for " + dataset + "********");
				lp.loginToSightLine(Input.pa1userName, Input.pa1password);
				for (int i = 0; i < dataset.size(); i++) {

					bc = new BaseClass(driver);
					bc.selectproject();

					IngestionPage page1 = new IngestionPage(driver);
					if (!page1.AddOnlyNewIngestion(dataset.get(i).toString())) {
						System.out.println("Execution aborted!, Ingestion did not go well! take a look and continue.");

						UtilityLog.info("Execution aborted!, Ingestion did not go well! take a look and continue. ");
						System.exit(1);

					}

					SessionSearch search = new SessionSearch(driver);

					int count = search.basicMetaDataSearch("IngestionName", null, page1.IngestionName, null);
					if (dataset.get(i).toString().equals("Automation_Collection1K_Tally")) {
						if (count != 1003) {
							System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
							System.out.println(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							System.exit(1);
						} else
							UtilityLog.info(dataset + " has all the documnets");
					} else if (dataset.get(i).toString().equals("Automation_20Family_20Threaded")) {
						if (count != 74) {
							System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
							System.out.println(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							System.exit(1);
						} else
							UtilityLog.info(dataset + " has all the documnets");
					} else if (dataset.get(i).toString().equals("Automation_AllSources")) {
						if (count != 125) {
							System.out.println("Execution aborted!");
							UtilityLog.info("Execution aborted!");
							System.out.println(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							UtilityLog.info(dataset.get(i).toString()
									+ " : Docs number mismatch! take a look and then continue the execution!!");
							System.exit(1);
						} else
							UtilityLog.info(dataset + " has all the documnets");
					}
					if (!search.bulkReleaseIngestions("Default Security Group")) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println("Bulk relese did not go well! take a look and continue!!");
						UtilityLog.info("Bulk relese did not go well! take a look and continue!!");
						System.exit(1);
					}
					lp.logout();
				}
				System.out.println("******Ingestion Completed********");
				UtilityLog.info("******Ingestion Completed********");
			} else {
				System.out.println("Choosen not to perform any ingestion");
				UtilityLog.info("Choosen not to perform any ingestion");
			}
		} catch (Exception e) {
			System.out.println("Execution aborted!, Something went wrong during ingestion.");
			UtilityLog.info("Execution aborted!, Something went wrong during ingestion.");
			System.exit(1);
		}

	}

//Below function loads test data according to the selection made in main config	
	public void loadSuiteTestData() throws IOException {
		FileInputStream Fread = null;
		if (Input.suite.equalsIgnoreCase("smoke") && numberOfDataSets == 1)
			Fread = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/configsAndTestData/" + "TestData_Smoke.xml");
		else if (Input.suite.equalsIgnoreCase("smoke") && numberOfDataSets == 3)
			Fread = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/configsAndTestData/" + "TestData_Regression.xml");
		else if (Input.suite.equalsIgnoreCase("Regression") && numberOfDataSets == 3)
			Fread = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/configsAndTestData/" + "TestData_Regression.xml");

		FileOutputStream Fwrite = new FileOutputStream(
				System.getProperty("user.dir") + "/src/main/java/configsAndTestData/" + "TestData.xml");
		int c;
		while ((c = Fread.read()) != -1)
			Fwrite.write((char) c);
		Fread.close();
		Fwrite.close();

		System.out.println("Test data is copied to TestData.xml");
		UtilityLog.info("Test data is copied to TestData.xml");
	}

//to make sure all docs released to default SG -helpful when ingestion done manually!
	public void releaseDocs() {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch("*");
		search.bulkRelease("Default Security Group");
		System.out.println("Docs released to default security group!");
		UtilityLog.info("Docs released to default security group!");
		lp.logout();
	}

}






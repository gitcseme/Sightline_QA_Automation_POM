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

	// Default should be true, Make it false during Development
	public static boolean mode = true;

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
// Added by krishna - new projects 
	public static String additionalDataProject;
	public static String ingestDataProject;
	public static String largeVolDataProject;
	public static String OnnaUrl;
	public static String OnnaDirectUrl;
	public static String ProductionPathAdditonal;
	// Environment data---------------------------------------------

	public static String url;
	public static String prodPath;
	public static String projectName;
	public static String projectNameCjk;
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
	public static String pa3FullName;
	public static String pa3userName;
	public static String pa3password;
	public static String rmu3FullName;
	public static String rmu3userName;
	public static String rmu3password;
	public static String rev3FullName;
	public static String rev3userName;
	public static String rev3password;
//	public static String pa4FullName;
	public static String pa4userName;
	public static String pa4password;
//	public static String rmu4FullName;
	public static String rmu4userName;
	public static String rmu4password;
//	public static String rev4FullName;
	public static String rev4userName;
	public static String rev4password;
//	public static String pa5FullName;
	public static String pa5userName;
	public static String pa5password;
//	public static String rmu5FullName;
	public static String rmu5userName;
	public static String rmu5password;
//	public static String rev5FullName;
	public static String rev5userName;
	public static String rev5password;
	public static String pa6userName;
	public static String pa6password;
//	public static String rmu5FullName;
	public static String rmu6userName;
	public static String rmu6password;
//	public static String rev5FullName;
	public static String rev6userName;
	public static String rev6password;
	public static String pa7userName;
	public static String pa7password;
//	public static String rmu5FullName;
	public static String rmu7userName;
	public static String rmu7password;
//	public static String rev5FullName;
	public static String rev7userName;
	public static String rev7password;
	public static String pa8userName;
	public static String pa8password;
//	public static String rmu5FullName;
	public static String rmu8userName;
	public static String rmu8password;
//	public static String rev5FullName;
	public static String rev8userName;
	public static String rev8password;
	public static String pa9userName;
	public static String pa9password;
//	public static String rmu5FullName;
	public static String rmu9userName;
	public static String rmu9password;
//	public static String rev5FullName;
	public static String rev9userName;
	public static String rev9password;
	public static String pa10userName;
	public static String pa10password;
//	public static String rmu5FullName;
	public static String rmu10userName;
	public static String rmu10password;
//	public static String rev5FullName;
	public static String rev10userName;
	public static String rev10password;
	public static String pa11userName;
	public static String pa11password;
//	public static String rmu5FullName;
	public static String rmu11userName;
	public static String rmu11password;
//	public static String rev5FullName;
	public static String rev11userName;
	public static String rev11password;
	public static String domainName;
	public static String da1FullName;
	public static String Onnapa1userName;
	public static String Onnarmu1userName;
	public static String Onnada1userName;
	public static String Onnasa1userName;
	public static String Onnapa1password;
	public static String Onnarmu1password;
	public static String Onnada1password;
	public static String Onnasa1password;
	public static String Onnarmu1FullName;
	public static String Onnapa1FullName;
	public static String Onnaeditpa1userName;
	public static String Onnaeditrmu1userName;
	public static String Onnaeditrmu1FullName;
	public static String Onnaeditpa1FullName;
	public static String Onnaeditpa1password;
	public static String Onnaeditrmu1password;
	

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
	public static String SmokeDatasetUploadData;

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
	public static String conceptualDocs1;
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
	public static String sourceDocId7;
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
	public static String threadData1;
	public static String nearDupeBulkAssign;
	public static String nearDupeBulkAssignId;
	public static String nearDupeBlukAssignReviewer;
	public static String nearDupeBulkAssignReviewId;
	public static String theardMapViewId;
	public static String nearDupeViewDocId;
	public static String threadMapNewId;
	public static String conceptualDocId1;
	public static String nearDupeDocId1;
	public static String nearDupeImageDoc;
	public static String analyticsConceptualDocId1;
	public static String analyticsConceptualDocId2;
	public static String miniConceptualNoDoc;
	public static String tiffSearchQuery;
	public static String familyDocWhichIsNotInMiniDoc;
	public static String bgColorOnMouseHover;
	public static String fieldByValue;
	public static String nearDupePagination;
	public static String highlightDocId;
	public static String highlightedDocsQuery;
	public static String ingestionQuery;
	public static String ingestionDocIdFamilyMember;
	public static String ingestionDocIdNearDupe;
	public static String nearDupeDoc05;
	public static String nearDoc1;
	public static String familyDoc1;
	public static String conceptDoc1;
	public static String highlightDoc1;
	public static String warning01;
	public static String ingestionQuery01;
	public static String sourceDocId10;
	public static String sourceDocId11;
	public static String sourceDocId12;
	public static String projectName01;
	public static String projectName02;
	public static String searchDocFileType;
	public static String DocViewNativesIngestion;

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
	public static String translationDocumentId;
	public static String parentDocument;
	public static String masterDateText;
	public static String pdfFileType;
	public static String reviewerProgress;
	public static String ReviewRateTrend;
	public static String TotalReviewProgress;
	public static String EndtoEnd;
	public static String ReviewerProductivity;
	public static String Tagging;
	public static String ToDoDocs;
	public static String Complete;
	public static String BeginBates;
	public static String NativeSourceDocId;
	public static String TextSourceDocId;
	public static String ExeTypeSourceDocId;

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
	public static String testSecondDocId;
	public static String testTenthDocId;
	public static String docNearDupeDocId;
	public static String UniCodeDocId;
	public static String TiffDocId;
	public static String IconOriginalColour;
	public static String DocIdWithHiddenContent;
	public static String TextHidden;
	public static String warningMessage;
	public static String HiddenContentExcelSheet;
	public static String HiddenContentExcelBook;
	public static String HiddenContentExternalLink;
	public static String audioString1;
	public static String audioString2;
	public static String sourceDocIdSearch;
	public static String DocIdWithComments;
	public static String DocIdWithTrackChanges;
	public static String HiddenIngestionDocId;
	public static String HiddenIngestionName;
	public static String HiddenLinkDocId;
	public static String pdfDocId;
	public static String xlsExcelDocId;
	public static String tiffDocId1;
	public static String pptDocId;
	public static String messageDocId;
	public static String multiplePageSourceDocID;
	public static String StitchedTiffSourceDocID;
	public static String SinglePageTiffSourceDocID;
	public static String DocIdCopyPaste;
	public static String DocIdCopyPaste1;
	public static String searchnameenron;

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

	// added by arun
	public static String pageRange1;
	public static String pageRange2;
	public static String pageRange3;
	public static String pageRange4;
	public static String searchPhraseWithQuote;
	public static String keywordColor1;
	public static String multiwordText;
	public static String query;
	public static String miniDocListID;
	public static String DATFile3;
	public static String indexingWarningMessage;
	public static String mappingWarningMessage;
	public static String nonExistingDataError;
	public static String audio96DocsFolder;
	public static String docIdKey;
	public static String selectNativeFile;
	public static String selectTextFile;
	public static String selectMp3File;
	public static String audioDatFile;
	public static String skippedAnalyticMessage;
	public static String completedAnalyticMessage;
	public static String emailName;
	public static String emailAddress1;
	public static String emailAddress2;
	public static String emailDatFile;
	public static String nonSearchablePdfLoadFile;
	public static String kickOffAnalytics;
	public static String incrementalAnalytics;
	public static String kickOffHelpContent;
	public static String incrementalHelpContent;
	public static String mp3Variant;
	public static String audio;
	public static String email;
	public static String emailToNamesAndAddresses;
	public static String datLoadFile2;
	public static String nativeLoadFile2;
	public static String textLoadFile2;
	public static String defaultNewLineDelimiter;
	public static String is;
	public static String range;
	public static String DocDateDateOnly;
	public static String nuix;
	public static String mappedData;
	public static String docPrimaryLanguage;
	public static String english;
	public static String yearRange1;
	public static String yearRange2;
	public static String translation;
	public static String related;
	public static String mandatoryMappingError;
	public static String analyticStatus;
	public static String docUnpublishedError;
	public static String sourceDocs;
	public static String copiedDocs;
	public static String missedDocs;
	public static String text;
	public static String datLoadFile3;
	public static String emailTextFile;
	public static String documentIdKey;
	public static String custIdKey;
	public static String fieldMappingWarningMessage;
	public static String datFileASCII;
	public static String datFile5;
	public static String datLoadFile4;
	public static String nativeLoadFile3;
	public static String textLoadFile3;
	public static String datFile6;
	public static String datFile7;
	public static String attachDocFolder;
	public static String folder61759;
	public static String sourceParentDocId;
	public static String overlayDatFile;
	public static String sourceKeyDocId;
	public static String char400Dat;
	public static String pdfPathDat;
	public static String pdfPathKey;
	public static String char400Error;
	public static String hiddenPropDat;
	public static String hiddenPropNative;
	public static String uncPath;
	public static String uncPathFolder;
	public static String uncAbsoluteDat;
	public static String uncAbsoluteTranslation;
	public static String uncAbsoluteTranscript;
	public static String nativePathField;
	public static String pdfPathField;
	public static String mp3PathField;
	public static String tiffPathField;
	public static String transcriptPathField;
	public static String uncAbsoluteNative;
	public static String uncRelativeDat;
	public static String absoluteOverlayDat;
	public static String relativeOverlayDat;
	public static String uncAbsoluteText;
	public static String absoluteOverlayText;
	public static String H13696smallSetFolder;
	public static String smallSetDat;
	public static String uncAbsolutePdf;
	public static String uncRelativeNative;
	public static String emailConversationGdFolder;
	public static String emailGdDat;
	public static String smallSetDat2;
	public static String specialCjkDat;

	// Added by Raghuram - Modified 02/24/22
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
	public static String errMsgSinceExecutionInProgress;
	public static String validBatchFileLocation;
	public static String fileDataErrMsg;
	public static String batchColumnHeaderErrorFileName;
	public static String BatchFileWithMultiplesheetFile;
	public static String savedSearchColor;
	public static String searchString8;
	public static String specialString1;
	public static String specialString2;
	public static String specialString3;
	public static String specialString4;
	public static String docSelectionHighlight;
	public static String batchFileWithMultiSheetColumnMissing;
	public static String batchFileWithMultiSheetColumnDuplicate;
	public static String searchString9;
	public static String batchFileWithColumnOrderChange;
	public static String batchFileWithMultiSheetColumnOrderChange;
	public static String highVolumeProject;
	public static String bulkSearchSting1;
	public static String validBatchFile;
	public static String masterDateBatchFile;
	public static String totalDirSubClusterText;
	public static String analyzeAt2;
	public static String analyzeAt3;
	public static String collectionPageUrl;
	public static String sourceLocationPageUrl;
	public static String collectionDataEmailId;
	public static String collectionDataFirstName;
	public static String collectionDataLastName;
	public static String collectionDataselectedApp;
	public static String collectionDataHeader1;
	public static String collectionDataHeader2;
	public static String collectionDataHeader3;
	public static String collectionDataHeader4;
	public static String collectionDataHeader5;
	public static String collectionDataHeader6;
	public static String collectionIdHeader;
	public static String collectionStatusHeader;
	public static String expectedDateInput;
	public static String expectedToDateInput;
	public static String searchString10;
	public static String dataSourceHeader;
	public static String creatingDSstatus;
	public static String retreivingDSstatus;
	public static String virusScanStatus;
	public static String copyDSstatus;
	public static String collectionExtractionMsg;
	public static String cancelCollectionNotification;
	public static String progressBarHeader;
	public static String splCharEmailFolder;
	public static String collectionProgressH;
    public static String collectionStatusH;
    public static String collectionErrColorCodeOrange;
    public static String retreivingDSCountH;
    public static String dateKeywordHeaderC;
    public static String clientCollectionEmail01;
    public static String clientcollectionFirstName01;
    public static String clientcollectionSecondName01;
    public static String clientCollectionEmail02;
    public static String clientcollectionFirstName02;
    public static String clientcollectionSecondName02;
    public static String copyDSwithErr;
    
	// Added BY Jeevitha
	public static String colorCodeOfRed;
	public static String crammerdocumentID;
	public static String batchFileNewLocation;
	public static String performaceBatchFile;
	public static String bullHornIconColor;
	public static String progresBarColor;
	public static String BatchFileduplicateHeader;
	public static String SearchBatchFile;
	public static String AutomationBackUpDomain;
	public static String RetrievalCountFold;
	
	public static String Searching;
	public static String DownloadNative;
	public static String Highlighting;
	public static String Redactions;
	public static String ReviewerRemarks;
	public static String AnalyticsPanels;
	public static String Manage;
	public static String Productions;
	public static String AllReports;
	public static String ConceptExplorer;
	public static String CommunicationsExplorer;
	public static String Categorize;
	public static String Datasets;
	public static String ManageDomainProjects;
	public static String Ingestions;
	
	// Added By Jeevitha [Collection]
	public static String TenantID;
	public static String ApplicationID;
	public static String ApplicationKey;
	public static String collection2ndEmailId;
	public static String collsecondFirstName;
	public static String collsecondlastName;
	public static String totalRetrievedCount;
    public static String collectionFailedStatus;
    
    public static String TenantIDJeb;
    public static String ApplicationIDJeb;
    public static String ApplicationKeyJeb;
    public static String collectionJebEmailId;

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

	// Added by Gopinath - 16/11/2021
	public static String signDocumentId;
	public static String technicalIssue;
	public static String crammerDocId;
	public static String produced;
	public static String producedDeleted;

	public static String EmailAuthourName;
	public static String MetaDataEAName;
	public static String MetaDataDomainName;
	public static String MetaDataReciepientsDomain;
	public static String KeyWordColour;
	// Added by Gopinath - 22/11/2021
	public static String batesDocumentId;

//	Added by baskar
	public static String tinyInt;
	public static String smallInt;
	public static String averageInt;
	public static String bigInt;
	public static String hugeInt;
	public static String docBasic;

	// Added by Gopinath - 25/11/2021
	public static String exText;

	// Added by Gopinath - 29/11/2021
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

	// added by Aathith
	public static String pageCount;
	public static String errorMsg;
	public static String helpText;
	public static String thisHidden;
	public static String thisReadOnly;
	public static String thisOptional;
	public static String thisRequired;
	public static String select;
	public static String notSelect;
	public static String securityGroup_sg47;
	public static String regressionConsilio;
	public static String regressionConsilio1;
	public static String audioSearchString2;
	public static String audioSearchString3;
	public static String audioSearchString4;
	public static String audioSearchString5;
	public static String audioSearchString6;
	public static String audioSearchString7;
	public static String pdfDataSet;
	public static String dbFile;
	public static String sourceDocIdDB992;
	public static String NonDomainProject;
	public static String emailAuthorDomain;
	public static String overlayOnly;
	public static String PDFGen_10Docs;
	public static String PDF5DocsLst;
	public static String Images5DocsLst;
	public static String newdateformat_5Docs;
	public static String JanMultiPTIFF;
	public static String GNon_searchable_PDF_Load_file;
	public static String BEbomDat;
	public static String MP3_OverlayLst;
	public static String ingestionAutomationAllSource;
	public static String SystemAdministrator;
	public static String DomainAdministrator;
	public static String ProjectAdministrator;
	public static String ReviewManager;
	public static String Reviewer;

	// Added by Brundha-1/12/2021
	public static String documentId;
	public static String telecaSearchString;
	public static String docFile;
	public static String stampRed;
	public static String stampGreen;
	public static String fileGroup;
	public static String document;
	public static String fileTypeInNativeDocs;
	public static String filterMonth;
	public static String filterYear;
	public static String userRole;
	public static String nativeFileName;
	public static String audioPlayerReady;
	public static String datFormatFile;
	public static String nativeMp3FileFormat;
	public static String DocCount;
	public static String emailAllDomain;
	public static String validationData;
	public static String ingestionPrjt;
	public static String AutomationAllSources;
	public static String TiffImages;
	public static String advancedOptionText;
	public static String sourceDocument;
	public static String DatFieldClassification;
	public static String DatSourceClassification;
	public static String MetaDataFileType;
	public static String NativeFileType;
	public static String FirstNameError;
	public static String LastNameError;
	public static String RoleError;
	public static String EmailAddressError;
	// Added by Gopinath- 30/11/2021
	public static String postGenQcChecks;

	// Added by Gopinath - 03/12/2021
	public static String thankyouText;

	// Added by gopinath - 08/12/2021
	public static String anotherRemarkMessage;

	// Added by gopinath - 09/12/2021
	public static String familyMembersDocId;

	// Added by Gopinath - 15/Dec/2021
	public static String proximity;
	public static String masterDate;

	// Added by gopinath - 16/Dec/2021
	public static String productionText;
	public static String tiffPageCountNam;
	public static String tiffPageCountText;

	// Added by Gopinath - 22/12/2021
	public static String errorQueryFileLocation;
	public static String invalidNameText;

	public static String threadDocWithToolTip;
	public static String conceptualDoc;

	// added by jayanthi-30/12/21
	public static String TallySearch;
	public static String TallyCN;
	public static String sameThreadDocs_EmailThreadID;
	public static String sameFamilyDocs_FamilyID;
	public static String IngestionName_PT;
	public static String IngestionName_UAT;
	public static String metadataIngestion;
	public static String custodianName_Andrew;
	public static String custodianName_allen;
	public static String SearchString_Audio;
	public static String SearchString_HighVolume;
	public static String DocCount_BG_Page;
	// Added by Gopinath - 05/01/2021
	public static String downloadDocID;
	public static String translationDocId;
	public static String TermOperator;

	// Added by Iyappan
	public static String hiddenDocId;
	// Added by Baskar
	public static String oneHourAudio;
	public static String excelProtectedHiddenDocId;

	// Added by Mohan (Ingestion Datasets)
	public static String H1369Folder;
	public static String MultiPTIFFFolder;
	public static String SinglePageTIFFFolder;
	public static String CJK_FrenchAudioTestDataFolder;
	public static String EmailConcatenatedDataFolder;
	public static String SSAudioSpeechFolder;
	public static String GD994NativeTextForProductionFolder;
	public static String GNonsearchablePDFLoadfileFolder;
	public static String HiddenPropertiesFolder;
	public static String UniCodeFilesFolder;
	public static String IngestionEmailDataFolder;
	public static String DATFile;
	public static String TextFile;
	public static String NativeFile;
	public static String TIFFFile;
	public static String TIFFFile1;
	public static String TranslationFile;
	public static String PDFFile;
	public static String TranscriptFile;
	public static String MP3File;
	public static String DATFile_S;
	public static String TextFile_S;
	public static String NativeFile_S;
	public static String TIFFFile_S;
	public static String TIFFFile1_S;
	public static String TranslationFile_S;
	public static String PDFFile_S;
	public static String TranscriptFile_S;
	public static String MP3File_S;
	public static String DATGermanFile;
	public static String DATJapneseFile;
	public static String TranscriptGermanFile;
	public static String MP3GermanFile;
	public static String TranscriptJapneseFile;
	public static String MP3JapneseFile;
	public static String SourceDatFieldCustom;
	public static String YYYYMMDDHHMISSDat;
	public static String YYYYMMDDHHMISSLst;
	public static String DATFile1;
	public static String StitchedTIFF;
	public static String DATFile2;
	public static String TiffImagesFolder;
	public static String DAT_MMDDYYYY;
	public static String Natives_MMDDYYYY;
	public static String DAT_YYYYDDMM_Slash;
	public static String Natives_YYYYDDMM_Slash;
	public static String DAT_YYYYMMDD_Slash;
	public static String Natives_YYYYMMDD_Slash;
	public static String DAT_DDMMYYYY_Slash;
	public static String Natives_DDMMYYYY_Slash;
	public static String DAT_MMDDYYYY_Slash;
	public static String Natives_MMDDYYYY_Slash;
	public static String DAT_MMDDYYYY_HHMI;
	public static String Natives_MMDDYYYY_HHMI;
	public static String DAT_DDMMYYYY_HHMI;
	public static String Natives_DDMMYYYY_HHMI;
	public static String DAT_MMDDYYYY_HHMISS;
	public static String Natives_MMDDYYYY_HHMISS;
	public static String DAT_DDMMYYYY_HHMISS;
	public static String Natives_DDMMYYYY_HHMISS;
	public static String DAT_DDMMYYYY;
	public static String Natives_DDMMYYYY;
	public static String differentDateFormatError;
	public static String tiffLoadFile;
	public static String prodBeg;
	public static String tiffFile2;
	public static String generateSearchablePDF;
	public static String DATFile4;
	public static String AK_NativeFolder;
	public static String duplicateIngestionError;
	public static String datLoadFile1;
	public static String textFile1;
	public static String DATPPPDF10Docs;
	public static String PP_PDFGen_10Docs;
	public static String TextPPPDF10Docs;
	public static String ImagePPPDF10docs;
	public static String sourceDocIDPPPDF10Docs;

	// Added by Gopinath - 02/03/2022
	public static String ingestionProjectName;
	public static String ingestionType;
	public static String sourceSystem;
	public static String sourceLocation;
	public static String sourceFolder;
	public static String fieldSeperator;
	public static String textQualifier;
	public static String multiValue;
	public static String datLoadFile;
	public static String documentKey;
	public static String mp3LoadFile;
	public static String dateFormat;
	public static String docId;
	public static String dataSource;
	public static String custodian;
	public static String fileExt;
	public static String fileName;
	public static String fileSize;
	public static String fileType;
	public static String ingDocBasic;
	public static String docFileExt;
	public static String ingDocFileName;
	public static String ingDocFileSize;
	public static String ingDocFileType;
	public static String lessThan500PagesDocId;
	public static String d500PagesDocId;

	// Added by Mohan for PT Env analytics panel
	public static String conceptualDocId01;
	public static String conceptualDocId02;
	public static String conceptualDocId03;
	public static String conceptualDocIdForReviewer01;
	public static String conceptualDocIdForReviewer02;
	public static String conceptualDocIdForReviewer03;
	public static String familyDocId01;
	public static String familyDocId02;
	public static String familyDocId03;
	public static String familyDocIdForReviewer01;
	public static String familyDocIdForReviewer02;
	public static String familyDocIdForReviewer03;
	public static String nearDupeDocId01;
	public static String nearDupeDocId02;
	public static String nearDupeDocId03;
	public static String nearDupeDocIdForReviewer01;
	public static String nearDupeDocIdForReviewer02;
	public static String nearDupeDocIdForReviewer03;
	public static String threadMapDocId01;
	public static String threadMapWithNonEmailAttachment;
	public static String conceptualDocId04;
	public static String defaultPdfDocId;

	// Added by Gopinath -21/03/2022
	public static String iceSourceSystem;
	public static String multiPageTIFFSourceFolder;
	public static String multiPageTIFFDATLoadFile;
	public static String documentKeyBNum;
	public static String documentKeyDSource;
	public static String documentKeyCName;

	// Added by gopinath - 23/03/2022
	public static String pageNum;

	// Added by Iyappan
	public static String maxAudioThresholdValue;
	public static String minAudioThresholdValue;
	public static String defaultAudioThresholdValue;
	public static int expectedCombinedSearchHits1;
	public static int expectedCombinedSearchHits2;
	public static String expectedPH_german;
	public static String expectedPH_Japanese;
	public static String langName_german;
	public static String langName_japnese;
	public static String createDate;
	public static String highVolumeProjSearchString;

	public static String cfDocId1;
	public static String cfDocId2;

	// Added by Gopinath - 08/04/2022
	public static String warningMsgDocId;
	public static String externalLinkDocId;

	// Added by Gopinath -28/09/2022
	public static String telecom;
	public static String regressionRun;
	public static String sourceParentBlank;
	public static String sourceBlank;
	public static String sourceBlank1;
	public static String ingestionLessThanHour;
	public static String ingestionOneHour;

	// Added by Iyappan
	public static String docAudioId1;
	public static String docAudioId2;

	// Added by Raghuram
	public static String preBuilt;
	public static String preBuiltHelpText;
	public static String DEPIPTheft;
	public static String Discrimination;
	public static String FCPA;
	public static String Harassment;
	public static String NR_Detection;
	public static String PII;
	public static String PRIV;
	public static String reteriveDSErr;
 	public static String completedWithErr;
 	public static String TheftOfCustomerInfo;
 	public static String EUPII;
 	 
 	public static String GenericPIITerms;
 	public static String ChildRelatedContent;
    public static String SexualOrientation;
    public static String PregnancyORDivorce;
    public static String GDPRTermsEnglish;
    public static String K;
    public static String child;
    public static String doctor;
    public static String firing;
    public static String homo;
    public static String homesex;
	public static String TheftOfCustomerInfoOutsideHelp;




	// Added by Aathith ,Client Page details
	public static String FilterByType;
	public static String Name;
	public static String ShortName;
	public static String Type;
	public static String DomainID;
	public static String ProcessingEngine;
	public static String CreatedBy;
	public static String CreatedON;
	public static String Actions;
	public static String Delete;
	public static String Edit;

	// Added by jayanthi
	public static String emailRecipientName;
	public static String emailRecipientName1;
	public static String domainNameConsilio;
	public static String domainNameSymphony;
	public static String custodianName;
	public static String emailToAdress;
	public static String emailCCAdress;
	public static String emailCCAdressOption1;
	public static String emailCCAdressOption2;
	public static String emailReciepientName2;
	public static String emailAllDomainOption;
	public static String masterDateOption;
	public static String emailAddressinput1;
	public static String emailAddressinput2;
	public static String emailAddressinput3;
	public static String filterDataInput;
	public static String filterDataInput1;
	public static String filterDataInput2;
    public static String filterDataInput3; 
	public static String transcriptId;
	public static String env;
	
	public static String Involvedoutsiders;
    public static String OutsideHelp;
    public static String SalaryMatch;
	
	@BeforeSuite(alwaysRun = true)

	public void loadEnvConfig() throws ParseException, InterruptedException, IOException {

		System.out.println("*****************************************************");
		UtilityLog.info("*****************************************************");
		// Common Data-------------------------------------------------------------
		config = (ConfigMain) new ConfigLoader().load("ConfigMain");
		if (mode) {
			// Reading from Jenkins
			envConfig = (Environment) new ConfigLoader().load(System.getProperty("environment"));
			System.out.println("Running scripts on " + System.getProperty("environment") + " Environment");
			UtilityLog.info("Running scripts on " + System.getProperty("environment") + " Environment");
		} else {
			// Reading from Config Main File
			envConfig = (Environment) new ConfigLoader().load(config.getEnv());
			System.out.println("Running scripts on " + config.getEnv() + " Environment");
			UtilityLog.info("Running scripts on " + config.getEnv() + " Environment");
		}

		newProject = config.getNewProject();
		ingestion = config.getIngestion();
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
		env = config.getEnv();
		// Added by krishna - new projects

		if (mode) {
			browserName = System.getProperty("browser");
			suite = System.getProperty("testType");
			numberOfDataSets = Integer.parseInt(System.getProperty("noOfDataSet"));
		} else {
			suite = config.getSuite();
			numberOfDataSets = config.getNumberOfDataSets();
			browserName = config.getBrowserName();
		}

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
		projectNameCjk = envConfig.getProjectNameCjk();
		pa1FullName = envConfig.getPa1FullName();
		rmu1FullName = envConfig.getRmu1FullName();
		rev1FullName = envConfig.getRev1FullName();
		pa2FullName = envConfig.getPa2FullName();
		rmu2FullName = envConfig.getRmu2FullName();
		rev2FullName = envConfig.getRev2FullName();
		da1FullName = envConfig.getDa1FullName();
		SourceLocation = envConfig.getSourceLocation();

		if (mode) {
			projectName = System.getProperty("projectName");
			projectNameCjk = System.getProperty("projectNameCjk");
			ICEProjectName = System.getProperty("ICEProjectName");
			domainName = System.getProperty("domainName");
			da1userName = System.getProperty("da1userName");
			da1password = System.getProperty("da1password");
			sa1userName = System.getProperty("sa1userName");
			sa1password = System.getProperty("sa1password");
			pa1userName = System.getProperty("pa1userName");
			pa1password = System.getProperty("pa1password");
			rmu1userName = System.getProperty("rmu1userName");
			rmu1password = System.getProperty("rmu1password");
			rev1userName = System.getProperty("rev1userName");
			rev1password = System.getProperty("rev1password");
			pa2userName = System.getProperty("pa2userName");
			pa2password = System.getProperty("pa2password");
			rmu2userName = System.getProperty("rmu2userName");
			rmu2password = System.getProperty("rmu2password");
			rev2userName = System.getProperty("rev2userName");
			rev2password = System.getProperty("rev2password");
			pa3userName = System.getProperty("pa3userName");
			pa3password = System.getProperty("pa3password");
			rmu3userName = System.getProperty("rmu3userName");
			rmu3password = System.getProperty("rmu3password");
			rev3userName = System.getProperty("rev3userName");
			rev3password = System.getProperty("rev3password");
			pa4userName = System.getProperty("pa4userName");
			pa4password = System.getProperty("pa4password");
			rmu4userName = System.getProperty("rmu4userName");
			rmu4password = System.getProperty("rmu4password");
			rev4userName = System.getProperty("rev4userName");
			rev4password = System.getProperty("rev4password");		
			pa5userName = System.getProperty("pa5userName");
			pa5password = System.getProperty("pa5password");
			rmu5userName = System.getProperty("rmu5userName");
			rmu5password = System.getProperty("rmu5password");
			rev5userName = System.getProperty("rev5userName");
			rev5password = System.getProperty("rev5password");
			pa6userName = System.getProperty("pa6userName");
			pa6password = System.getProperty("pa6password");
			rmu6userName = System.getProperty("rmu6userName");
			rmu6password = System.getProperty("rmu6password");
			rev6userName = System.getProperty("rev6userName");
			rev6password = System.getProperty("rev6password");
			pa7userName = System.getProperty("pa7userName");
			pa7password = System.getProperty("pa7password");
			rmu7userName = System.getProperty("rmu7userName");
			rmu7password = System.getProperty("rmu7password");
			rev7userName = System.getProperty("rev7userName");
			rev7password = System.getProperty("rev7password");
			pa8userName = System.getProperty("pa8userName");
			pa8password = System.getProperty("pa8password");
			rmu8userName = System.getProperty("rmu8userName");
			rmu8password = System.getProperty("rmu8password");
			rev8userName = System.getProperty("rev8userName");
			rev8password = System.getProperty("rev8password");
			pa9userName = System.getProperty("pa9userName");
			pa9password = System.getProperty("pa9password");
			rmu9userName = System.getProperty("rmu9userName");
			rmu9password = System.getProperty("rmu9password");
			rev9userName = System.getProperty("rev9userName");
			rev9password = System.getProperty("rev9password");
			pa10userName = System.getProperty("pa10userName");
			pa10password = System.getProperty("pa10password");
			rmu10userName = System.getProperty("rmu10userName");
			rmu10password = System.getProperty("rmu10password");
			rev10userName = System.getProperty("rev10userName");
			rev10password = System.getProperty("rev10password");
			pa11userName = System.getProperty("pa11userName");
			pa11password = System.getProperty("pa11password");
			rmu11userName = System.getProperty("rmu11userName");
			rmu11password = System.getProperty("rmu11password");
			rev11userName = System.getProperty("rev11userName");
			rev11password = System.getProperty("rev11password");
			additionalDataProject = System.getProperty("additionalDataProject");
			ingestDataProject = System.getProperty("ingestDataProject");
			largeVolDataProject = System.getProperty("largeVolDataProject");
			prodPath = System.getProperty("prodpath");

		} else {
			projectName = envConfig.getProjectName();
			projectNameCjk = envConfig.getProjectNameCjk();
			ICEProjectName = envConfig.getICEProjectName();
			domainName = envConfig.getDomainName();
			da1userName = envConfig.getDa1userName();
			da1password = envConfig.getDa1password();
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
			pa3userName = envConfig.getPa3userName();
			pa3password = envConfig.getPa3password();
			rmu3userName = envConfig.getRmu3userName();
			rmu3password = envConfig.getRmu3password();
			rev3userName = envConfig.getRev3userName();
			rev3password = envConfig.getRev3password();
			pa4userName = envConfig.getPa4userName();
			pa4password = envConfig.getPa4password();
			rmu4userName = envConfig.getRmu4userName();
			rmu4password = envConfig.getRmu4password();
			rev4userName = envConfig.getRev4userName();
			rev4password = envConfig.getRev4password();
			pa5userName = envConfig.getPa5userName();
			pa5password = envConfig.getPa5password();
			rmu5userName = envConfig.getRmu5userName();
			rmu5password = envConfig.getRmu5password();
			rev5userName = envConfig.getRev5userName();
			rev5password = envConfig.getRev5password();
			pa6userName = envConfig.getPa6userName();
			pa6password = envConfig.getPa6password();
			rmu6userName = envConfig.getRmu6userName();
			rmu6password = envConfig.getRmu6password();
			rev6userName = envConfig.getRev6userName();
			rev6password = envConfig.getRev6password();
			pa7userName = envConfig.getPa7userName();
			pa7password = envConfig.getPa7password();
			rmu7userName = envConfig.getRmu7userName();
			rmu7password = envConfig.getRmu7password();
			rev7userName = envConfig.getRev7userName();
			rev7password = envConfig.getRev7password();
			pa8userName = envConfig.getPa8userName();
			pa8password = envConfig.getPa8password();
			rmu8userName = envConfig.getRmu8userName();
			rmu8password = envConfig.getRmu8password();
			rev8userName = envConfig.getRev8userName();
			rev8password = envConfig.getRev8password();
			pa9userName = envConfig.getPa9userName();
			pa9password = envConfig.getPa9password();
			rmu9userName = envConfig.getRmu9userName();
			rmu9password = envConfig.getRmu9password();
			rev9userName = envConfig.getRev9userName();
			rev9password = envConfig.getRev9password();
			pa10userName = envConfig.getPa10userName();
			pa10password = envConfig.getPa10password();
			rmu10userName = envConfig.getRmu10userName();
			rmu10password = envConfig.getRmu10password();
			rev10userName = envConfig.getRev10userName();
			rev10password = envConfig.getRev10password();
			pa11userName = envConfig.getPa11userName();
			pa11password = envConfig.getPa11password();
			rmu11userName = envConfig.getRmu11userName();
			rmu11password = envConfig.getRmu1password();
			rev11userName = envConfig.getRev11userName();
			rev11password = envConfig.getRev11password();
			additionalDataProject = envConfig.getAdditionalDataProject();
			ingestDataProject = envConfig.getIngestDataProject();
			largeVolDataProject = envConfig.getLargeVolDataProject();
			prodPath = envConfig.getProdpath();

		}
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
		conceptualDocs1 = testData.getConceptualDocs1();
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
		nearDupeViewDocId = testData.getNearDupeViewDocId();
		nearDupeCompletedDocId = testData.getNearDupeCompletedDocId();
		nearDupeDocId = testData.getNearDupeDocId();
		nearDupeCompletedDocIdReviewer = testData.getNearDupeCompletedDocIdReviewer();
		familyDocumentForReviewer = testData.getFamilyDocumentForReviewer();
		historyClockIconDocId = testData.getHistoryClockIconDocId();
		warningDocId = testData.getWarningDocId();
		threadData1 = testData.getThreadData1();
		nearDupeBulkAssign = testData.getNearDupeBulkAssign();
		nearDupeBulkAssignId = testData.getNearDupeBulkAssignId();
		nearDupeBlukAssignReviewer = testData.getNearDupeBulkAssignReviewer();
		nearDupeBulkAssignReviewId = testData.getNearDupeBulkAssignReviewId();
		theardMapViewId = testData.getTheardMapViewId();
		threadMapNewId = testData.getThreadMapNewId();
		conceptualDocId1 = testData.getConceptualDocId1();
		nearDupeDocId1 = testData.getNearDupeDocId1();
		nearDupeImageDoc = testData.getNearDupeImageDoc();
		analyticsConceptualDocId1 = testData.getAnalyticsConceptualDocId1();
		analyticsConceptualDocId2 = testData.getAnalyticsConceptualDocId2();
		miniConceptualNoDoc = testData.getMiniConceptualNoDoc();
		tiffSearchQuery = testData.getTiffSearchQuery();
		familyDocWhichIsNotInMiniDoc = testData.getFamilyDocWhichIsNotInMiniDoc();
		bgColorOnMouseHover = testData.bgColorOnMouseHover;
		nearDupePagination = testData.getNearDupePagination();
		fieldByValue = testData.getFieldByValue();
		highlightDocId = testData.getHighlightDocId();
		highlightedDocsQuery = testData.getHighlightedDocsQuery();
		ingestionQuery = testData.getIngestionQuery();
		ingestionDocIdFamilyMember = testData.getIngestionDocIdFamilyMember();
		ingestionDocIdNearDupe = testData.getIngestionDocIdNearDupe();
		nearDupeDoc05 = testData.getNearDupeDoc05();
		nearDoc1 = testData.getNearDoc1();
		familyDoc1 = testData.getFamilyDoc1();
		conceptDoc1 = testData.getConceptDoc1();
		highlightDoc1 = testData.getHighlightDoc1();
		warning01 = testData.getWarning01();
		ingestionQuery01 = testData.getIngestionQuery01();
		sourceDocId10 = testData.getSourceDocId10();
		sourceDocId11 = testData.getSourceDocId11();
		sourceDocId12 = testData.getSourceDocId12();
		projectName01 = testData.getProjectName01();
		projectName02 = testData.getProjectName02();
		searchDocFileType = testData.getSearchDocFileType();
		DocViewNativesIngestion = testData.getDocViewNativesIngestion();

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
		ExeTypeSourceDocId=testData.getExeTypeSourceDocId();
		TextSourceDocId=testData.getTextSourceDocId();
		NativeSourceDocId=testData.getNativeSourceDocId();
		testData1 = testData.getTestData1();
		tagNamePrev = testData.getTagNamePrev();
		tagNameTechnical = testData.getTagNameTechnical();
		documentID = testData.getDocumentID();
		orphanDocument = testData.getOrphanDocument();
		searchString3 = testData.getSearchString3();
		searchString4 = testData.getSearchString4();
		signatureDocumentID = testData.getSignatureDocumentID();
		searchString20Docs = testData.getSearchString20Docs();
		prodPathUAT = testData.getProdPathUAT();
		bates = testData.getBates();
		batesNumber = testData.getBatesNumber();
		ChildBates = testData.getChildBates();
		Production = testData.getProduced();
		TIFFPageCount = testData.getPageCount();
		translationDocumentId = testData.getTranslationDocId();
		parentDocument = testData.getParentDocument();
		masterDateText = testData.getMasterDateText();
		pdfFileType = testData.getPdfFileType();
		reviewerProgress = testData.getReviewerProgress();
		ReviewRateTrend = testData.getReviewRateTrend();
		TotalReviewProgress = testData.getTotalReviewProgress();
		EndtoEnd = testData.getEndtoEnd();
		Tagging = testData.getTagging();
		ReviewerProductivity = testData.getReviewerProductivity();
		ToDoDocs = testData.getToDoDocs();
		Complete = testData.getComplete();
		BeginBates=testData.getBeginBates();
		
		/*
		 * ICE Upload Data
		 * 
		 */
		SmokeDatasetUploadData=testData.getSmokeDatasetUploadData();

		/**
		 * @author Aathith.Senthilkumar
		 */
		pageCount = testData.getPageCount();
		errorMsg = testData.getErrorMsg();
		helpText = testData.getHelpText();
		thisHidden = testData.getThisHidden();
		thisReadOnly = testData.getThisReadOnly();
		thisOptional = testData.getThisOptional();
		thisRequired = testData.getThisRequired();
		select = testData.getSelect();
		notSelect = testData.getNotSelect();
		securityGroup_sg47 = testData.getSecurityGroup_sg47();
		regressionConsilio = testData.getRegressionConsilio();
		regressionConsilio1 = testData.getRegressionConsilio1();
		audioSearchString2 = testData.getAudioSearchString2();
		audioSearchString3 = testData.getAudioSearchString3();
		audioSearchString4 = testData.getAudioSearchString4();
		audioSearchString5 = testData.getAudioSearchString5();
		audioSearchString6 = testData.getAudioSearchString6();
		audioSearchString7 = testData.getAudioSearchString7();
		pdfDataSet = testData.getPdfDataSet();
		dbFile = testData.getDbFile();
		sourceDocIdDB992 = testData.getSourceDocIdDB992();
		NonDomainProject = testData.getNonDomainProject();
		emailAuthorDomain = testData.getEmailAuthorDomain();
		overlayOnly = testData.getOverlayOnly();
		PDFGen_10Docs = testData.getPDFGen_10Docs();
		PDF5DocsLst = testData.getPDF5DocsLst();
		Images5DocsLst = testData.getImages5DocsLst();
		newdateformat_5Docs = testData.getNewdateformat_5Docs();
		JanMultiPTIFF = testData.getJanMultiPTIFF();
		GNon_searchable_PDF_Load_file = testData.getGNon_searchable_PDF_Load_file();
		BEbomDat = testData.getBEbomDat();
		MP3_OverlayLst = testData.getMP3_OverlayLst();
		ingestionAutomationAllSource = testData.getIngestionAutomationAllSource();
		SystemAdministrator = testData.getSystemAdministrator();
		DomainAdministrator = testData.getDomainAdministrator();
		ProjectAdministrator = testData.getProjectAdministrator();
		ReviewManager = testData.getReviewManager();
		Reviewer = testData.getReviewer();
		OnnaUrl=testData.getOnnaUrl();
		OnnaDirectUrl=testData.getOnnaDirectUrl();
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
		testSecondDocId = testData.getTestSecondDocId();
		testTenthDocId = testData.getTestTenthDocId();
		docNearDupeDocId = testData.getDocNearDupeDocId();
		UniCodeDocId = testData.getUniCodeDocId();
		TiffDocId = testData.getTiffDocId();
		HiddenContentExcelSheet = testData.getHiddenContentExcelSheet();
		HiddenContentExcelBook = testData.getHiddenContentExcelBook();
		HiddenContentExternalLink = testData.getHiddenContentExternalLink();
		audioString1 = testData.getAudioString1();
		audioString2 = testData.getAudioString2();
		HiddenIngestionDocId = testData.getHiddenIngestionDocId();
		HiddenIngestionName = testData.getHiddenIngestionName();
		HiddenLinkDocId = testData.getHiddenLinkDocId();
		defaultPdfDocId = testData.getDefaultPdfDocId();
		multiplePageSourceDocID = testData.getMultiplePageSourceDocID();
		StitchedTiffSourceDocID = testData.getStitchedTiffSourceDocID();
		SinglePageTiffSourceDocID = testData.getSinglePageTiffSourceDocID();

		// Added by Arun
		pageRange1 = testData.getPageRange1();
		pageRange2 = testData.getPageRange2();
		pageRange3 = testData.getPageRange3();
		pageRange4 = testData.getPageRange4();
		searchPhraseWithQuote = testData.getSearchPhraseWithQuote();
		keywordColor1 = testData.getKeywordColor1();
		multiwordText = testData.getMultiwordText();
		query = testData.getQuery();
		miniDocListID = testData.getMiniDocListID();
		DATFile3 = testData.getDATFile3();
		indexingWarningMessage = testData.getIndexingWarningMessage();
		mappingWarningMessage = testData.getMappingWarningMessage();
		nonExistingDataError = testData.getNonExistingDataError();
		audio96DocsFolder = testData.getAudio96DocsFolder();
		docIdKey = testData.getDocIdKey();
		selectNativeFile = testData.getSelectNativeFile();
		selectTextFile = testData.getSelectTextFile();
		selectMp3File = testData.getSelectMp3File();
		audioDatFile = testData.getAudioDatFile();
		skippedAnalyticMessage = testData.getSkippedAnalyticMessage();
		completedAnalyticMessage = testData.getCompletedAnalyticMessage();
		emailName = testData.getEmailName();
		emailAddress1 = testData.getEmailAddress1();
		emailAddress2 = testData.getEmailAddress2();
		emailDatFile = testData.getEmailDatFile();
		nonSearchablePdfLoadFile = testData.getNonSearchablePdfLoadFile();
		kickOffAnalytics = testData.getKickOffAnalytics();
		incrementalAnalytics = testData.getIncrementalAnalytics();
		kickOffHelpContent = testData.getKickOffHelpContent();
		incrementalHelpContent = testData.getIncrementalHelpContent();
		mp3Variant = testData.getMp3Variant();
		audio = testData.getAudio();
		email = testData.getEmail();
		emailToNamesAndAddresses = testData.getEmailToNamesAndAddresses();
		datLoadFile2 = testData.getDatLoadFile2();
		nativeLoadFile2 = testData.getNativeLoadFile2();
		textLoadFile2 = testData.getTextLoadFile2();
		defaultNewLineDelimiter = testData.getDefaultNewLineDelimiter();
		is = testData.getIs();
		range = testData.getRange();
		DocDateDateOnly = testData.getDocDateDateOnly();
		nuix = testData.getNuix();
		mappedData = testData.getMappedData();
		docPrimaryLanguage = testData.getDocPrimaryLanguage();
		english = testData.getEnglish();
		yearRange1 = testData.getYearRange1();
		yearRange2 = testData.getYearRange2();
		translation = testData.getTranslation();
		related = testData.getRelated();
		mandatoryMappingError = testData.getMandatoryMappingError();
		analyticStatus = testData.getAnalyticStatus();
		docUnpublishedError = testData.getDocUnpublishedError();
		sourceDocs = testData.getSourceDocs();
		copiedDocs = testData.getCopiedDocs();
		missedDocs = testData.getMissedDocs();
		text = testData.getText();
		datLoadFile3 = testData.getDatLoadFile3();
		emailTextFile = testData.getEmailTextFile();
		documentIdKey = testData.getDocumentIdKey();
		custIdKey = testData.getCustIdKey();
		fieldMappingWarningMessage = testData.getFieldMappingWarningMessage();
		datFileASCII = testData.getDatFileASCII();
		datFile5 = testData.getDatFile5();
		datLoadFile4 = testData.getDatLoadFile4();
		nativeLoadFile3 = testData.getNativeLoadFile3();
		textLoadFile3 = testData.getTextLoadFile3();
		datFile6 = testData.getDatFile6();
		datFile7 = testData.getDatFile7();
		attachDocFolder= testData.getAttachDocFolder();
		folder61759 = testData.getFolder61759();
		sourceParentDocId = testData.getSourceParentDocId();
		overlayDatFile = testData.getOverlayDatFile();
		sourceKeyDocId = testData.getSourceKeyDocId();	
		char400Dat = testData.getChar400Dat();
		pdfPathDat = testData.getPdfPathDat();
		pdfPathKey = testData.getPdfPathKey();
		char400Error = testData.getChar400Error();
		hiddenPropDat = testData.getHiddenPropDat();
		hiddenPropNative = testData.getHiddenPropNative();
		uncPath = testData.getUncPath();
		uncPathFolder = testData.getUncPathFolder();
		uncAbsoluteDat = testData.getUncAbsoluteDat();
		uncAbsoluteTranslation = testData.getUncAbsoluteTranslation();
		uncAbsoluteTranscript = testData.getUncAbsoluteTranscript();
		nativePathField = testData.getNativePathField();
		pdfPathField = testData.getPdfPathField();
		mp3PathField = testData.getMp3PathField();;
		tiffPathField = testData.getTiffPathField();
		transcriptPathField = testData.getTranscriptPathField();
		uncAbsoluteNative = testData.getUncAbsoluteNative();
		uncRelativeDat = testData.getUncRelativeDat();
		absoluteOverlayDat = testData.getAbsoluteOverlayDat();
		relativeOverlayDat = testData.getRelativeOverlayDat();
		uncAbsoluteText = testData.getUncAbsoluteText();
		absoluteOverlayText = testData.getAbsoluteOverlayText();
		H13696smallSetFolder = testData.getH13696smallSetFolder();
		smallSetDat = testData.getSmallSetDat();
		uncAbsolutePdf = testData.getUncAbsolutePdf();
		uncRelativeNative = testData.getUncRelativeNative();
		emailConversationGdFolder = testData.getEmailConversationGdFolder();
		emailGdDat = testData.getEmailGdDat();
		smallSetDat2 = testData.getSmallSetDat2();
		specialCjkDat = testData.getSpecialCjkDat();
		ProductionPathAdditonal = testData.getProductionPathAdditonal();
		
		
		// Added by Raghuram 02/24/22
		docSelectionHighlight = testData.getDocSelectionHighlight();
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
		errMsgSinceExecutionInProgress = testData.getErrMsgSinceExecutionInProgress();
		validBatchFileLocation = testData.getValidBatchFileLocation();
		fileDataErrMsg = testData.getFileDataErrMsg();
		batchColumnHeaderErrorFileName = testData.getBatchColumnHeaderErrorFileName();
		BatchFileWithMultiplesheetFile = testData.getBatchFileWithMultiplesheetFile();
		savedSearchColor = testData.getSavedSearchColor();
		searchString8 = testData.getSearchString8();
		specialString1 = testData.getSpecialString1();
		specialString2 = testData.getSpecialString2();
		specialString3 = testData.getSpecialString3();
		specialString4 = testData.getSpecialString4();
		batchFileWithMultiSheetColumnMissing = testData.getBatchFileWithMultiSheetColumnMissing();
		batchFileWithMultiSheetColumnDuplicate = testData.getBatchFileWithMultiSheetColumnDuplicate();
		searchString9 = testData.getSearchString9();
		batchFileWithColumnOrderChange = testData.getBatchFileWithColumnOrderChange();
		batchFileWithMultiSheetColumnOrderChange = testData.getBatchFileWithMultiSheetColumnOrderChange();
		highVolumeProject = testData.getHighVolumeProject();
		bulkSearchSting1 = testData.getBulkSearchSting1();
		validBatchFile = testData.getValidBatchFile();
		masterDateBatchFile = testData.getMasterDateBatchFile();
		totalDirSubClusterText = testData.getTotalDirSubClusterText();
		analyzeAt2 = testData.getAnalyzeAt2();
		analyzeAt3 = testData.getAnalyzeAt3();
		collectionPageUrl = testData.getCollectionPageUrl();
		sourceLocationPageUrl = testData.getSourceLocationPageUrl();
		collectionDataEmailId = testData.getCollectionDataEmailId();
		collectionDataFirstName = testData.getCollectionDataFirstName();
		collectionDataLastName = testData.getCollectionDataLastName();
		collectionDataselectedApp = testData.getCollectionDataselectedApp();
		collectionDataHeader1 = testData.getCollectionDataHeader1();
		collectionDataHeader2 = testData.getCollectionDataHeader2();
		collectionDataHeader3 = testData.getCollectionDataHeader3();
		collectionDataHeader4 = testData.getCollectionDataHeader4();
		collectionDataHeader5 = testData.getCollectionDataHeader5();
		collectionDataHeader6 = testData.getCollectionDataHeader6();
		collectionIdHeader = testData.getCollectionIdHeader();
		collectionStatusHeader = testData.getCollectionStatusHeader();
		expectedDateInput = testData.getExpectedDateInput();
		expectedToDateInput = testData.getExpectedToDateInput();
		searchString10 = testData.getSearchString10();
		dataSourceHeader = testData.getDataSourceHeader();
		creatingDSstatus = testData.getCreatingDSstatus();
		retreivingDSstatus = testData.getRetreivingDSstatus();
		virusScanStatus = testData.getVirusScanStatus();
		copyDSstatus = testData.getCopyDSstatus();
		collectionExtractionMsg = testData.getCollectionExtractionMsg();
		cancelCollectionNotification = testData.getCancelCollectionNotification();
		progressBarHeader = testData.getProgressBarHeader();
		splCharEmailFolder = testData.getSplCharEmailFolder();
		collectionProgressH = testData.getCollectionProgressH();
        collectionStatusH = testData.getCollectionStatusH();
        collectionErrColorCodeOrange = testData.getCollectionErrColorCodeOrange();
        retreivingDSCountH = testData.getRetreivingDSCountH();
        dateKeywordHeaderC = testData.getDateKeywordHeaderC();	
         reteriveDSErr = testData.getReteriveDSErr();
         completedWithErr  = testData.getCompletedWithErr();
		clientCollectionEmail01 = testData.getClientCollectionEmail01();
		clientcollectionFirstName01 = testData.getClientcollectionFirstName01();
		clientcollectionSecondName01 = testData.getClientcollectionSecondName01();
		clientCollectionEmail02 = testData.getClientCollectionEmail02();
		clientcollectionFirstName02 = testData.getClientcollectionFirstName02();
		clientcollectionSecondName02 = testData.getClientcollectionSecondName02();
		copyDSwithErr = testData.getCopyDSwithErr();
            
		// Added by Jeevitha
		colorCodeOfRed = testData.getColorCodeOfRed();
		metaDataCN = testData.getMetaDataCN();
		batchFileNewLocation = testData.getBatchFileNewLocation();
		bullHornIconColor = testData.getBullHornIconColor();
		progresBarColor = testData.getProgresBarColor();
		BatchFileduplicateHeader = testData.getBatchFileduplicateHeader();
		metaDataCNcount = testData.getMetaDataCNcount();
		SearchBatchFile = testData.getSearchBatchFile();
		AutomationBackUpDomain = testData.getAutomationBackUpDomain();
		RetrievalCountFold= testData.getRetrievalCountFold();
		
		//
		Searching= testData.getSearching();
		DownloadNative= testData.getDownloadNative();
		Highlighting= testData.getHighlighting();
		Redactions= testData.getRedactions();
		ReviewerRemarks= testData.getReviewerRemarks();
		AnalyticsPanels= testData.getAnalyticsPanels();
		Manage= testData.getManage();
		Productions= testData.getProductions();
		AllReports= testData.getAllReports();
		ConceptExplorer= testData.getConceptExplorer();
		CommunicationsExplorer= testData.getCommunicationsExplorer();
		Categorize= testData.getCategorize();
		Datasets= testData.getDatasets();
		ManageDomainProjects= testData.getManageDomainProjects();
		Ingestions= testData.getIngestions();
		
		// Added By Jeevitha [Collection]
		TenantID = testData.getTenantID();
		ApplicationID = testData.getApplicationID();
		ApplicationKey = testData.getApplicationKey();
		collection2ndEmailId = testData.getCollection2ndEmailId();
		collsecondFirstName = testData.getCollsecondFirstName();
		collsecondlastName = testData.getCollsecondlastName();
		totalRetrievedCount = testData.getTotalRetrievedCount();
		collectionFailedStatus= testData.getCollectionFailedStatus();
		
		// Added by Mohan
		conceptualDocId01 = testData.getConceptualDocId01();
		conceptualDocId02 = testData.getConceptualDocId02();
		conceptualDocId03 = testData.getConceptualDocId03();
		conceptualDocId04 = testData.getConceptualDocId04();
		conceptualDocIdForReviewer01 = testData.getConceptualDocIdForReviewer01();
		conceptualDocIdForReviewer02 = testData.getConceptualDocIdForReviewer02();
		conceptualDocIdForReviewer03 = testData.getConceptualDocIdForReviewer03();
		familyDocId01 = testData.getFamilyDocId01();
		familyDocId02 = testData.getFamilyDocId02();
		familyDocId03 = testData.getFamilyDocId03();
		familyDocIdForReviewer01 = testData.getFamilyDocIdForReviewer01();
		familyDocIdForReviewer02 = testData.getFamilyDocIdForReviewer02();
		familyDocIdForReviewer03 = testData.getFamilyDocIdForReviewer03();
		nearDupeDocId01 = testData.getNearDupeDocId01();
		nearDupeDocId02 = testData.getNearDupeDocId02();
		nearDupeDocId03 = testData.getNearDupeDocId03();
		nearDupeDocIdForReviewer01 = testData.getNearDupeDocIdForReviewer01();
		nearDupeDocIdForReviewer02 = testData.getNearDupeDocIdForReviewer02();
		nearDupeDocIdForReviewer03 = testData.getNearDupeDocIdForReviewer03();
		threadMapDocId01 = testData.getThreadMapDocId01();
		threadMapWithNonEmailAttachment = testData.getThreadMapWithNonEmailAttachment();
		collectionJebEmailId=testData.getCollectionJebEmailId();
		TenantIDJeb=testData.getTenantIDJeb();
		ApplicationIDJeb=testData.getApplicationIDJeb();
		ApplicationKeyJeb=testData.getApplicationKeyJeb();
		
		
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

		// Added
		WPbatchFile = testData.WPbatchFile;

		// Added by Iyappan
		WPbatchFile = testData.getWPbatchFile();

		// Added by Krishna
		docIdKeyWordTest = testData.getDocIdKeyWordTest();
		keyWordHexCode = testData.getKeyWordHexCode();
		IconOriginalColour = testData.getIconOriginalColour();
		DocIdWithHiddenContent = testData.getDocIdWithHiddenContent();
		TextHidden = testData.getTextHidden();
		warningMessage = testData.getWarningMessage();
		DocIdWithComments = testData.getDocIdWithComments();
		DocIdWithTrackChanges = testData.getDocIdWithTrackChanges();
		pdfDocId = testData.getPdfDocId();
		xlsExcelDocId = testData.getXlsExcelDocId();
		tiffDocId1 = testData.getTiffDocId1();
		pptDocId = testData.getPptDocId();
		messageDocId = testData.getMessageDocId();
		DocIdCopyPaste = testData.getDocIdCopyPaste();
		DocIdCopyPaste1 = testData.getDocIdCopyPaste1();
		// Added by Gopinath - 16/11/2021
		signDocumentId = testData.getSignDocumentId();
		technicalIssue = testData.getTechnicalIssue();
		crammerDocId = testData.getCrammerDocId();
		produced = testData.getProduced();
		producedDeleted = testData.getProducedDeleted();
		// added by jayanthi
		EmailAuthourName = testData.getEmailAuthourName();
		MetaDataEAName = testData.getMetaDataEAName();
		MetaDataDomainName = testData.getMetaDataDomainName();
		MetaDataReciepientsDomain = testData.getMetaDataReciepientsDomain();
		sourceDocIdSearch = testData.getSourceDocIdSearch();
		KeyWordColour = testData.getKeyWordColour();
		sourceDocId7 = testData.getSourceDocId7();
		// Added By jeevitha
		performaceBatchFile = testData.getPerformaceBatchFile();

		// Added by gopinath-22/11/2021
		batesDocumentId = testData.getProducedDeleted();

//	    Added by Baskar 
		tinyInt = testData.getTinyInt();
		smallInt = testData.getSmallInt();
		averageInt = testData.getAverageInt();
		bigInt = testData.getBigInt();
		hugeInt = testData.getHugeInt();
		docBasic = testData.getDocBasic();

		// Added by Gopianth - 25/11/2021
		exText = testData.exText;

		// Added by Gopinath - 29/11/2021
		paginationDocId = testData.paginationDocId;
//	    Added by Baskar -29/11/2021
		hidden = testData.getHidden();
		optional = testData.getOptional();
		notSelectable = testData.getNotSelectable();
		required = testData.getRequired();
		tag = testData.getTag();
		comments = testData.getComments();
		metaData = testData.getMetaData();
		checkItem = testData.getCheckItem();
		staticText = testData.getStaticText();
		radioGroup = testData.getRadioGroup();
		checkGroup = testData.getCheckGroup();
		yesButton = testData.getYesButton();
		searchnameenron = testData.getSearchnameenron();	
		// Added by Brundha-1/12/2021

		documentId = testData.getDocumentID();
		telecaSearchString = testData.getTelecaSearchString();
		docFile = testData.getDocFile();
		stampRed = testData.getStampRed();
		stampGreen = testData.getStampGreen();
		fileGroup = testData.getFileGroup();
		document = testData.getDocument();
		fileTypeInNativeDocs = testData.getFileTypeInNativeDocs();
		filterMonth = testData.getFilterMonth();
		filterYear = testData.getFilterYear();
		userRole = testData.getUserRole();
		nativeFileName = testData.getNativeFileName();
		audioPlayerReady = testData.getAudioPlayerReady();
		datFormatFile = testData.getDatFormatFile();
		nativeMp3FileFormat = testData.getNativeMp3FileFormat();
		DocCount = testData.getDocCount();
		emailAllDomain = testData.getEmailAllDomain();
		validationData = testData.getValidationData();
		ingestionPrjt = testData.getIngestionPrjt();
		AutomationAllSources = testData.getAutomationAllSources();
		TiffImages = testData.getTiffImages();
		advancedOptionText = testData.getAdvancedOptionText();
		sourceDocument = testData.getSourceDocument();
		DatFieldClassification = testData.getDatFieldClassification();
		DatSourceClassification = testData.getDatSourceClassification();
		MetaDataFileType = testData.getMetaDataFileType();
		NativeFileType = testData.getNativeFileType();
		FirstNameError=testData.getFirstNameError();
		LastNameError=testData.getLastNameError();
		RoleError=testData.getRoleError();
		EmailAddressError=testData.getEmailAddressError();

		// Added by Gopinath - 30/11/2021
		postGenQcChecks = testData.getPostGenQcChecks();

		// Added by Gopinath - 03/12/2021
		thankyouText = testData.getThankyouText();

		// Added by Gopinath - 08/12/2021
		anotherRemarkMessage = testData.getAnotherRemarkMessage();

		// Added by Gopinath - 09/12/2021
		familyMembersDocId = testData.getFamilyMembersDocId();

		// Added by Gopinath - 15/Dec/2021
		proximity = testData.getProximity();
		masterDate = testData.getMasterDate();

		// Added by Gopinath - 16/Dec/2021
		productionText = testData.getProductionText();
		tiffPageCountNam = testData.getTiffPageCountNam();
		tiffPageCountText = testData.getTiffPageCountText();

		// Added by Gopinath - 27/12/2021
		errorQueryFileLocation = testData.getErrorQueryFileLocation();
		invalidNameText = testData.getInvalidNameText();

		threadDocWithToolTip = testData.getThreadDocWithToolTip();
		conceptualDoc = testData.getConceptualDoc();
//added by jayanthi
		TallySearch = testData.getTallySearch();
		TallyCN = testData.getTallyCN();
		sameThreadDocs_EmailThreadID = testData.getSameThreadDocs_EmailThreadID();
		sameFamilyDocs_FamilyID = testData.getSameFamilyDocs_FamilyID();
		IngestionName_PT = testData.getIngestionName_PT();
		IngestionName_UAT = testData.getIngestionName_UAT();
		metadataIngestion = testData.getMetadataIngestion();
		custodianName_Andrew = testData.getCustodianName_Andrew();
		custodianName_allen = testData.getCustodianName_allen();
		SearchString_Audio = testData.getSearchString_Audio();
		SearchString_HighVolume = testData.getSearchString_HighVolume();
		DocCount_BG_Page = testData.getDocCount_BG_Page();
		TermOperator = testData.getTermOperator();

		// Added by Gopinath 05/01/2021
		downloadDocID = testData.getDownloadDocID();
		translationDocId = testData.getTranslationDocId();

		conceptualSearchString1 = testData.getConceptualSearchString1();
		// Added by Iyappan
		hiddenDocId = testData.getHiddenDocId();
		// Added by baskar
		oneHourAudio = testData.getOneHourAudio();

		hiddenDocId = testData.getHiddenDocId();
		excelProtectedHiddenDocId = testData.getExcelProtectedHiddenDocId();

		// Ingestion DataSet

		H1369Folder = testData.getH1369Folder();
		MultiPTIFFFolder = testData.getMultiPTIFFFolder();
		SinglePageTIFFFolder = testData.getSinglePageTIFFFolder();
		CJK_FrenchAudioTestDataFolder = testData.getCJK_FrenchAudioTestDataFolder();
		EmailConcatenatedDataFolder = testData.getEmailConcatenatedDataFolder();
		SSAudioSpeechFolder = testData.getSSAudioSpeechFolder();
		GD994NativeTextForProductionFolder = testData.getGD994NativeTextForProductionFolder();
		GNonsearchablePDFLoadfileFolder = testData.getGNonsearchablePDFLoadfileFolder();
		HiddenPropertiesFolder = testData.getHiddenPropertiesFolder();
		UniCodeFilesFolder = testData.getUniCodeFilesFolder();
		IngestionEmailDataFolder = testData.getIngestionEmailDataFolder();
		DATFile = testData.getDATFile();
		TextFile = testData.getTextFile();
		NativeFile = testData.getNativeFile();
		TIFFFile = testData.getTIFFFile();
		TIFFFile1 = testData.getTIFFFile1();
		TranslationFile = testData.getTranslationFile();
		PDFFile = testData.getPDFFile();
		TranscriptFile = testData.getTranscriptFile();
		MP3File = testData.getMP3File();
		DATFile_S = testData.getDATFile_S();
		TextFile_S = testData.getTextFile_S();
		NativeFile_S = testData.getNativeFile_S();
		TIFFFile_S = testData.getTIFFFile_S();
		TIFFFile1_S = testData.getTIFFFile1_S();
		TranslationFile_S = testData.getTranslationFile_S();
		PDFFile_S = testData.getPDFFile_S();
		TranscriptFile_S = testData.getTranscriptFile_S();
		MP3File_S = testData.getMP3File_S();
		DATGermanFile = testData.getDATGermanFile();
		DATJapneseFile = testData.getDATJapneseFile();
		TranscriptGermanFile = testData.getTranscriptGermanFile();
		MP3GermanFile = testData.getMP3GermanFile();
		TranscriptJapneseFile = testData.getTranscriptJapneseFile();
		MP3JapneseFile = testData.getMP3JapneseFile();
		YYYYMMDDHHMISSDat = testData.getYYYYMMDDHHMISSDat();
		YYYYMMDDHHMISSLst = testData.getYYYYMMDDHHMISSLst();
		StitchedTIFF = testData.getStitchedTIFF();
		DATFile1 = testData.getDATFile1();
		DATFile2 = testData.getDATFile2();
		TiffImagesFolder = testData.getTiffImagesFolder();
		DAT_MMDDYYYY = testData.getDAT_MMDDYYYY();
		Natives_MMDDYYYY = testData.getNatives_MMDDYYYY();
		DAT_YYYYDDMM_Slash = testData.getDAT_YYYYDDMM_Slash();
		Natives_YYYYDDMM_Slash = testData.getNatives_YYYYDDMM_Slash();
		DAT_YYYYMMDD_Slash = testData.getDAT_YYYYMMDD_Slash();
		Natives_YYYYMMDD_Slash = testData.getNatives_YYYYMMDD_Slash();
		DAT_DDMMYYYY_Slash = testData.getDAT_DDMMYYYY_Slash();
		Natives_DDMMYYYY_Slash = testData.getNatives_DDMMYYYY_Slash();
		DAT_MMDDYYYY_Slash = testData.getDAT_MMDDYYYY_Slash();
		Natives_MMDDYYYY_Slash = testData.getNatives_MMDDYYYY_Slash();
		DAT_MMDDYYYY_HHMI = testData.getDAT_MMDDYYYY_HHMI();
		Natives_MMDDYYYY_HHMI = testData.getNatives_MMDDYYYY_HHMI();
		DAT_DDMMYYYY_HHMI = testData.getDAT_DDMMYYYY_HHMI();
		Natives_DDMMYYYY_HHMI = testData.getNatives_DDMMYYYY_HHMI();
		DAT_MMDDYYYY_HHMISS = testData.getDAT_MMDDYYYY_HHMISS();
		Natives_MMDDYYYY_HHMISS = testData.getNatives_MMDDYYYY_HHMISS();
		DAT_DDMMYYYY_HHMISS = testData.getDAT_DDMMYYYY_HHMISS();
		Natives_DDMMYYYY_HHMISS = testData.getNatives_DDMMYYYY_HHMISS();
		DAT_DDMMYYYY = testData.getDAT_DDMMYYYY();
		Natives_DDMMYYYY = testData.getNatives_DDMMYYYY();
		differentDateFormatError = testData.getDifferentDateFormatError();
		tiffLoadFile = testData.getTiffLoadFile();
		prodBeg = testData.getProdBeg();
		tiffFile2 = testData.getTiffFile2();
		generateSearchablePDF = testData.getGenerateSearchablePDF();
		DATFile4 = testData.getDATFile4();
		AK_NativeFolder = testData.getAK_NativeFolder();
		duplicateIngestionError = testData.getDuplicateIngestionError();
		datLoadFile1 = testData.getDatLoadFile1();
		textFile1 = testData.getTextFile1();
		DATPPPDF10Docs = testData.getDATPPPDF10Docs();
		PP_PDFGen_10Docs = testData.getPP_PDFGen_10Docs();
		TextPPPDF10Docs = testData.getTextPPPDF10Docs();
		ImagePPPDF10docs = testData.getImagePPPDF10docs();
		sourceDocIDPPPDF10Docs = testData.getSourceDocIDPPPDF10Docs();

		// Added by Gopinath - 02/03/2022
		ingestionProjectName = testData.getIngestionProjectName();
		ingestionType = testData.getIngestionType();
		sourceSystem = testData.getSourceSystem();
		sourceLocation = testData.getSourceLocation();
		sourceFolder = testData.getSourceFolder();
		fieldSeperator = testData.getFieldSeperator();
		textQualifier = testData.getTextQualifier();
		multiValue = testData.getMultiValue();
		datLoadFile = testData.getDatLoadFile();
		documentKey = testData.getDocumentKey();
		mp3LoadFile = testData.getMp3LoadFile();
		dateFormat = testData.getDateFormat();
		docId = testData.getDocId();
		dataSource = testData.getDataSource();
		custodian = testData.getCustodian();
		fileExt = testData.getFileExt();
		fileName = testData.getFileName();
		fileSize = testData.getFileSize();
		fileType = testData.getFileType();
		ingDocBasic = testData.getIngDocBasic();
		docFileExt = testData.getDocFileExt();
		ingDocFileName = testData.getIngDocFileName();
		ingDocFileSize = testData.getIngDocFileSize();
		ingDocFileType = testData.getIngDocFileType();
		lessThan500PagesDocId = testData.getLessThan500PagesDocId();
		d500PagesDocId = testData.getD500PagesDocId();

		// Added by Gopinath -21/03/2022
		iceSourceSystem = testData.getIceSourceSystem();
		multiPageTIFFSourceFolder = testData.getMultiPageTIFFSourceFolder();
		multiPageTIFFDATLoadFile = testData.getMultiPageTIFFDATLoadFile();
		documentKeyBNum = testData.getDocumentKeyBNum();
		documentKeyDSource = testData.getDocumentKeyDSource();
		documentKeyCName = testData.getDocumentKeyCName();

		// Added by Gopinath -23/03/2022
		pageNum = testData.getPageNum();
		maxAudioThresholdValue = testData.getMaxAudioThresholdValue();
		minAudioThresholdValue = testData.getMinAudioThresholdValue();
		defaultAudioThresholdValue = testData.getDefaultAudioThresholdValue();
		expectedCombinedSearchHits1 = testData.getExpectedCombinedSearchHits1();
		expectedCombinedSearchHits2 = testData.getExpectedCombinedSearchHits2();
		expectedPH_german = testData.getExpectedPH_german();
		expectedPH_Japanese = testData.getExpectedPH_Japanese();
		langName_german = testData.getLangName_german();
		langName_japnese = testData.getLangName_japnese();
		createDate = testData.getCreateDate();
		highVolumeProjSearchString = testData.getHighVolumeProjSearchString();

		cfDocId1 = testData.getCfDocId1();
		cfDocId2 = testData.getCfDocId2();

		// Added by Gopinath - 08/04/2022
		warningMsgDocId = testData.getWarningMsgDocId();
		externalLinkDocId = testData.getExternalLinkDocId();

		// Added by Gopinath - 28/09/2022
		telecom = testData.getTelecom();
		regressionRun = testData.getRegressionRun();
		sourceParentBlank = testData.getSourceParentBlank();
		sourceBlank = testData.getSourceBlank();
		sourceBlank1 = testData.getSourceBlank1();
		ingestionOneHour = testData.getIngestionOneHour();
		ingestionLessThanHour = testData.getIngestionLessThanHour();

		// Added by Iyappan
		docAudioId1 = testData.getDocAudioId1();
		docAudioId2 = testData.getDocAudioId2();

		// Added By Raghuram
		preBuilt = testData.getPreBuilt();
		preBuiltHelpText = testData.getPreBuiltHelpText();
		DEPIPTheft = testData.getDEPIPTheft();
		Discrimination = testData.getDiscrimination();
		FCPA = testData.getFCPA();
		Harassment = testData.getHarassment();
		PII = testData.getPII();
		PRIV = testData.getPRIV();
        NR_Detection = testData.getNR_Detection();
        TheftOfCustomerInfo = testData.getTheftOfCustomerInfo();
        EUPII =testData.getEUPII();
        GenericPIITerms =testData.getGenericPIITerms();
        ChildRelatedContent =testData.getChildRelatedContent();
		SexualOrientation = testData.getSexualOrientation();  
		PregnancyORDivorce =testData.getPregnancyORDivorce();    
		GDPRTermsEnglish = testData.getGDPRTermsEnglish();    
		K = testData.getK();    
		child = testData.getChild();    
		doctor = testData.getDoctor();    
		firing = testData.getFiring();    
		homo = testData.getHomo();   
		homesex = testData.getHomesex();
		TheftOfCustomerInfoOutsideHelp= testData.getTheftOfCustomerInfoOutsideHelp();
		
		// Added by Aathith ,Client Page details
		FilterByType = testData.getFilterByType();
		Name = testData.getName();
		ShortName = testData.getShortName();
		Type = testData.getType();
		DomainID = testData.getDomainID();
		ProcessingEngine = testData.getProcessingEngine();
		CreatedBy = testData.getCreatedBy();
		CreatedON = testData.getCreatedON();
		Actions = testData.getActions();
		Delete = testData.getDelete();
		Edit = testData.getEdit();

		// Added by jayanthi
		emailRecipientName =testData.getEmailRecipientName();
		emailRecipientName1 =testData.getEmailRecipientName1();
		domainNameConsilio =testData.getDomainNameConsilio();
		domainNameSymphony =testData.getDomainNameSymphony();
		custodianName=testData.getCustodianName();
		emailToAdress= testData.getEmailToAdress();
		emailCCAdress=testData.getEmailCCAdress();
		emailCCAdressOption1= testData.getEmailCCAdressOption1();
		emailCCAdressOption2=testData.getEmailCCAdressOption2();
		emailReciepientName2=testData.getEmailReciepientName2();
		emailAllDomainOption=testData.getEmailAllDomainOption();
		masterDateOption =testData.getMasterDateOption();
		emailAddressinput1=testData.getEmailAddressinput1();
		emailAddressinput2=testData.getEmailAddressinput2();
		emailAddressinput3=testData.getEmailAddressinput3();
		filterDataInput=testData.getFilterDataInput();
		filterDataInput1=testData.getFilterDataInput1();
		filterDataInput2=testData.getFilterDataInput2();
        filterDataInput3=testData.getFilterDataInput3();  
        transcriptId=testData.getTranscriptId();
        
        Onnapa1userName=testData.getOnnapa1userName();
        Onnarmu1userName=testData.getOnnarmu1userName();
        Onnasa1userName=testData.getOnnasa1userName();
        Onnada1userName=testData.getOnnada1userName();
        Onnapa1password=testData.getOnnapa1password();
    	Onnarmu1password=testData.getOnnarmu1password();
    	Onnada1password=testData.getOnnada1password();
    	Onnasa1password=testData.getOnnasa1password();
    	Onnarmu1FullName=testData.getOnnarmu1FullName();
    	Onnapa1FullName=testData.getOnnapa1FullName();
    	Onnaeditrmu1FullName=testData.getOnnaeditrmu1FullName();
    	Onnaeditpa1FullName=testData.getOnnaeditpa1FullName();
    	Onnaeditpa1password=testData.getOnnaeditpa1password();
    	Onnaeditrmu1password=testData.getOnnaeditrmu1password();
    	Onnaeditpa1userName=testData.getOnnaeditpa1userName();
        Onnaeditrmu1userName=testData.getOnnaeditrmu1userName();
    	
    	
		System.out.println("****************************************************");
		UtilityLog.info("*****************************************************");

		
		 Involvedoutsiders = testData.getInvolvedoutsiders();
	        OutsideHelp = testData.getOutsideHelp();
	        SalaryMatch =testData.getSalaryMatch();
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


package configsAndTestData;

import lombok.AllArgsConstructor;
import lombok.Builder; 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@Builder(toBuilder = true)
//@NoArgsConstructor
//@AllArgsConstructor
public class TestData {

	// search
	public String searchString1;
	public String searchString2;
	public int pureHitSeachString1;
	public int pureHitSeachString2;
	public int searchString1ANDsearchString2;
	public int searchString1ORsearchString2;
	public int searchString1NOTsearchString2;
	public int searchString2NOTsearchString1;
	public String audioSearchString1;
	public int audioSearchString1pureHit; 
	public String conceptualSearchString1;
	public int conceptualSearchString1PureHit;
	public String metaDataCN;
	public int metaDataCNcount;
	public int totalNumberOfDocs;
	public int totalNumberOfDocsincludeadvoption;
	public String MasterPDF1location;
	public String MasterPDF2location;

	// Mohan Indium Docview Analytics
	public String Panel;
	public String AssgnName;
	public String TextValue;
	public String TextEmpty;
	public int pureHitCount;
	public String SavedSearch;
	public String FolderName;
	public String docName;
	public int pureHitCount1;
	public String MiniDocId;
	public String ThreadQuery;
	public String NewDocId;
	public String MetaDataId;
	public String documentToBeScrolled;
	public String conceptualDocument;
	public String conceptualDocs1;
	public String conceptualDocumentReviewer;
	public String threadDocId;
	public String principalDocId;
	public String principalDocId1;
	public String principalDocId2;
	public String principalDocId3;
	public String principalDocId4;
	public String principalDocId5;
	public String principalDocId6;
	public String sourceDocId; 
	public String sourceDocId1;
	public String sourceDocId2;
	public String sourceDocId3;
	public String sourceDocId4;
	public String sourceDocId5;
	public String nearDupeCompletedDocId;
	public String nearDupeCompletedDocIdReviewer;
	public String nearDupeDocId;
	public String nearDupeDocument;
	public String familyDocument;
	public String nearDupeDocumentForReviewer;
	public String threadDocumentForReviewer;
	public String familyDocumentForReviewer;
	public String historyClockIconDocId;
	public String newNearDupeDocId;
	public String warningDocId;
	public String threadData1;
	public String nearDupeBulkAssign;
	public String nearDupeBulkAssignId;
	public String nearDupeBulkAssignReviewer;
	public String nearDupeBulkAssignReviewId;
	public String theardMapViewId;
	public String nearDupeViewDocId;
	public String threadMapNewId;
	public String conceptualDocId1;
	public String nearDupeDocId1;
	public String nearDupeImageDoc;
	public String analyticsConceptualDocId1;
	public String analyticsConceptualDocId2;
	public String miniConceptualNoDoc;
	public String tiffSearchQuery;

	// Baskar Indium Docview Codingform
	public String savedName;
	public String codingFormName;
	public String assignmentSelection;
	public String stampColour;
	public String assignmentLevelStampOFF;
	public String searchText;
	public String reviewerName;
	public String stampColours;
	public String stampSelection;

	// Baskar Indium Audio Search to Docview MiniList

	public String language;
	public int documentIdNum;
	public String searchStringStar;
	public String docIDs;

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 */
	public String historyTab;
	public String documentConceptualTab;
	public String randomText;
	public String metaDataName;
	public String is_Or_Range;
	public String second_Input;
	public String defaultRedactionTag;
	public String fldClassification;
	public String fldDescription;
	public String fieldType;
	public String fieldLength;
	public String codeFormName;
	public String securityGroup;
	public String actionColumnName;
	public String nameColumnName;
	public String userColumnName;

	public String timeStampColumnName;
	// added by sowndariya

	public String searchString3;
	public String searchString4;
	public String testData1;
	public String tagNamePrev;
	public String tagNameTechnical;
	public String documentID;
	public String orphanDocument;
	public String signatureDocumentID;
	public String searchString20Docs;
	public String prodPathUAT;
	public String bates;
	public String batesNumber;
	public  String ChildBates;
	public  String Production;
	public  String TIFFPageCount;

	// Added by Sai Krishna
	public String pageNumber;
	public String searchDocId;
	public String searchBulletDocId;
	public String searchTestSG;
	public String duplicateDocId;
	public String pageRange;
	public String fullPageRange;
	public String iconColor;
	public String testSecondDocId;
	public String testTenthDocId;
	public String docNearDupeDocId;
	public String UniCodeDocId;
	public String TiffDocId;
	public String IconOriginalColour;
	public String DocIdWithHiddenContent;
	public String TextHidden;
	
	//Added by arunkumar
	public String pageRange1;
	public String pageRange2;
	public String pageRange3;
	public String pageRange4;
	public String searchPhraseWithQuote;
	public String keywordColor1;
	

	// Added by Raghuram - Last Modified 02/02/21 
	public String docHighlightColor;
	public String sortType;
	public String sortDataBy;
	public String fileDownloadLocation;
	public String completedWithErrColorCodeBR;
	public String shareSearchPA;
	public String shareSearchDefaultSG;
	public String searchString5;
	public String searchString6;
	public String mySavedSearch;
	public String zeroPureHitString;
	public String invalidBatchFileNewLocation;
	public String xlsBatchFile;
	public String invalidBatchFileFormatErrMsg;
	public String metaDataCustodianNameInput;
	public String selectionHighlightColor;
	public String searchString7;
	public String errMsgSinceExecutionInProgress;
	public String validBatchFileLocation;
	public String fileDataErrMsg;
	public String batchColumnHeaderErrorFileName;
	public String BatchFileWithMultiplesheetFile;
	public String savedSearchColor;
	public String searchString8;
	public String specialString1;
	public String specialString2;
	public String specialString3;

	// Added by jeevitha
	public String colorCodeOfRed;
	public String crammerdocumentID;
	public String batchFileNewLocation;
	public String bullHornIconColor;
	public String progresBarColor;
	public String BatchFileduplicateHeader;
	
	/**
	 * @author : Gopinath Created date: 01-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 */
	public String betweenTagName;

	/**
	 * @author : Gopinath Created date: 03-09-2021 Modified date: NA Modified
	 *         by:Gopinath.
	 */
	public String orderCriteria;
	public String orderType;

	// Added by Gopinath - 08-09-2021
	public String savedPageTitle;

	// Added by Gopinath
	public String comment;

	// Added by Gopinath - 21/09/2021
	public String codeSameAsLastMsg;

	// Added by Gopinath - 23/09/2021
	public String startTime;
	public String endTime;

	// Added by Jagadeesh.jana
	public String colorCodeHighlight;

	// Added by Gopinath - 27/09/2021
	public String pageLength;

	// Added by Gopinath - 30/09/2021
	public String date;

	// Added by Gopinath - 05/10/2021
	public String docFileSize;
	public String docFileName;
	
	 //Added by Gopinath - 06/10/2021
    public String docFileType;
    
    public String docIdRemarks;
    public String docIdRemarks2;
    
  //Added by Gopinath- 11/10/2021
  	public String atternoyClient;
  	public String confidential;
  	public String reviewed;
	public String documentComments;
	
	
	
	//Added by Gopinath - 25/10/2021
	public String audioSearch;
	public String audioLanguage;
	
	//Added by Gopinath - 27/10/2021
	public String excelFileDocument;
	public String mp3FileDocument;
	
	//Added by Iyappan - 1/11/2021
	public String WPbatchFile;
	
	//Added by Gopinath- 03/11/2021
	public String fileExtDiffDocument;
	public String fileExtBlankDocument;
	public String translationDocument;
	
	//Added by krishna 08/11/2021
	
	public String docIdKeyWordTest;
	public String keyWordHexCode;
	public String docIdThumbnails;
	

	
	
	//Added by Gopinath - 16/11/2021
		public String signDocumentId;
		public String technicalIssue;
		public String crammerDocId;
		public String produced;
		public String producedDeleted;

	public String performaceBatchFile;
	
	//Added by jayanthi
	public String EmailAuthourName;
	public String MetaDataEAName;
	public String MetaDataReciepientsDomain;
	public String MetaDataDomainName;
    public String KeyWordColour;
	public String signDocumentId() {
		// TODO Auto-generated method stub
		return null; 
	}
	
	//Added by Gopinath - 22/11/2021
		public String batesDocumentId;
		
//		Added by Baskar
		public String tinyInt; 
		public String smallInt;
		public String averageInt;
		public String bigInt;
		public String hugeInt;
		public String docBasic;
		
		//Added by Gopinath - 25/11/2021
		public String exText;
		
		//Added by Gopinath - 29/11/2021
		public String paginationDocId;
		
//		Added by Baskar -29/11/2021
		public String hidden;
		public String notSelectable;
		public String optional;
		public String required;
		public String tag;
		public String comments;
		public String metaData;
		public String checkItem; 
		public String staticText;
		public String radioGroup;
		public String checkGroup;
		public String yesButton;
		
		//added by Aathith
		public String pageCount;
		public String errorMsg;
		public String helpText;
		public String thisHidden;
		public String thisReadOnly;
		public String thisOptional;
		public String thisRequired;
		public String select;
		public String notSelect;
		public String securityGroup_sg47;
		public String regressionConsilio;
		public String regressionConsilio1;
	
		//Added by Gopinath - 30/11/2021
		public String postGenQcChecks;
		
		//Added by Gopinath - 03/12/2021
		public String thankyouText;

		//added by Brundha
		
		public String documentId;
		public String telecaSearchString;
		public String docFile;
		
		//Added by gopinath - 08/12/2021
		public String anotherRemarkMessage;
		

		//Added by gopinath - 09/12/2021
		public String familyMembersDocId;
		
		//Added by Gopinath - 15/Dec/2021
		public String proximity;
		public String masterDate;
		
		//Added by gopinath  - 16/Dec/2021
		public String productionText;
		public String tiffPageCountNam;
		public String tiffPageCountText;
		
		//Added by Gopinath - 27/12/2021
	    public String errorQueryFileLocation;
		public String invalidNameText;
				
		public String threadDocWithToolTip;
		public String conceptualDoc;
		//Added by jayanthi
		public String TallySearch;
		public String TallyCN;

		//Added by Gopinath - 05/01/2021
		public String downloadDocID;
		public String translationDocId;
}
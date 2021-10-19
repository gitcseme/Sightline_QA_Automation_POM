package configsAndTestData;


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
public class TestData {
	//search 
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
		public String  conceptualSearchString1;
		public int  conceptualSearchString1PureHit;
		public String  metaDataCN;
		public int  metaDataCNcount;
		public int totalNumberOfDocs;
		public int totalNumberOfDocsincludeadvoption;
		public String MasterPDF1location;
		public String MasterPDF2location;
		public String CustomFieldname;
}

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
public class Environment {
	public String url;
	public String projectName;
	public String projectNameCjk;
	public String domainName;
	public String sa1userName;
	public String sa1password;
	public String da1userName;
	public String da1password;
	public String pa1userName;
	public String pa1password;
	public String rmu1userName;
	public String rmu1password;
	public String rev1userName;
	public String rev1password;
	public String pa1FullName;
	public String rmu1FullName;
	public String rev1FullName;
	public String pa2FullName;
	public String rmu2FullName;
	public String rev2FullName;
	public String pa2userName;
	public String pa2password;
	public String rmu2userName;
	public String rmu2password;
	public String rev2userName;
	public String rev2password;
	public String prodpath;
	public String SourceLocation;
	public String shareTo;
	public String ICEProjectName;
// Added by Krishna	
	public String additionalDataProject;
	public String ingestDataProject;
	public String largeVolDataProject;

	// Added by sowndarya
	public String da1FullName;

}

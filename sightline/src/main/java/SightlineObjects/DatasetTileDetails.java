package SightlineObjects;

import java.util.Date;

public class DatasetTileDetails {
	
	private String datasetType;
	private String datasetName;
	private String custodianName;
	private int uploadedCount;
	private int publishedCount;
	private int errorCount;
	private String lastModifiedBy;
	private Date lastModifiedAt;
	private String lastStatus;
	private Date lastStatusAt;
	
	public void setDatasetType(String dType)
	{
		datasetType = dType;
	}
	
	public void setDatasetName(String dName)
	{
		datasetName = dName;
	}
	
	public void setCustodianName(String cName)
	{
		custodianName = cName;
	}
	
	public void setUploadedCount(int uCount)
	{
		uploadedCount = uCount;
	}
	
	public void setPublishedCount(int pCount)
	{
		publishedCount = pCount;
	}
	
	public void setErrorCount(int eCount)
	{
		errorCount = eCount;
	}
	
	public void setLastModifiedBy(String lMBy)
	{
		lastModifiedBy = lMBy;
	}
	
	public void setLastModifiedAt(Date lMAt)
	{
		lastModifiedAt = lMAt;
	}
	
	public void setLastStatus(String lStatus)
	{
		lastStatus = lStatus;
	}
	
	public void setLastStatusAt(Date lSAt)
	{
		lastStatusAt = lSAt;
	}
	
	public String getDatasetType()
	{
		return datasetType;
	}
	
	public String getDatasetName()
	{
		return datasetName;
	}
	
	public String getCustodianName()
	{
		return custodianName;
	}
	
	public int getUploadedCount()
	{
		return uploadedCount;
	}
	
	public int getPublishedCount()
	{
		return publishedCount;
	}
	
	public int getErrorCount()
	{
		return errorCount ;
	}
	
	public String getLastModifiedBy()
	{
		return lastModifiedBy;
	}
	
	public Date getLastModifiedAt()
	{
		return lastModifiedAt;
	}
	
	public String getLastStatus()
	{
		return lastStatus ;
	}
	
	public Date getLastStatusAt()
	{
		return lastStatusAt;
	}
	
	
}

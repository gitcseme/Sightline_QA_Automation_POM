package SightlineObjects;

import automationLibrary.Element;

public class FileUploadDetails {
	
	private String fileName;
	private String size;
	private Float status;
	private String error;
	private Element element;
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fName)
	{
		fileName = fName;
	}
	
	public Element getFileElement()
	{
		return element;
	}
	
	public void setFileElement(Element elem)
	{
		element = elem;
	}
	
	public String getFileSize()
	{
		return size;
	}
	
	public void setFileSize(String fSize)
	{
		size = fSize;
	}
	
	public Float getFileUploadStatus()
	{
		return status;
	}
	
	public void setFileUploadStatus(Float stat)
	{
		status = stat;
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String err)
	{
		error = err;
	}
	
	
	

}

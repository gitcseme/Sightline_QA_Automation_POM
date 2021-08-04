package SightlineObjects;

public class SLProcessingStates {
	
	boolean isProcessing = false;
	boolean isPostProcessing = false;
	boolean isPostProcessingComplete = false;
	boolean isEnrichingData=false;
	boolean isEnrichedData=false;
	boolean isIndexing=false;
	boolean isIndexed=false;
	boolean isAnalyticsRunning=false;
	boolean isAnalyticsComplete=false;
	boolean isPublished=false;
	
	public boolean isProcessing()
	{
		return isProcessing;
	}

	public void setProcessing(boolean value)
	{
		isProcessing = value;
	}
	
	public boolean isPostProcessing()
	{
		return isPostProcessing;
	}

	public void setPostProcessing(boolean value)
	{
		isPostProcessing = value;
	}

	public boolean isPostProcessingComplete()
	{
		return isPostProcessingComplete;
	}

	public void setPostProcessingComplete(boolean value)
	{
		isPostProcessingComplete = value;
	}

	public boolean isEnrichingData()
	{
		return isEnrichingData;
	}

	public void setEnrichingData(boolean value)
	{
		isEnrichingData = value;
	}
	public boolean isEnrichedData()
	{
		return isEnrichedData;
	}

	public void setEnrichedData(boolean value)
	{
		isEnrichedData = value;
	}
	public boolean isIndexing()
	{
		return isIndexing;
	}

	public void setIndexing(boolean value)
	{
		isIndexing = value;
	}
	public boolean isIndexed()
	{
		return isIndexed;
	}

	public void setIndexed(boolean value)
	{
		isIndexed = value;
	}
	public boolean isAnalyticsRunning()
	{
		return isAnalyticsRunning;
	}

	public void setAnalyticsRunning(boolean value)
	{
		isAnalyticsRunning = value;
	}
	public boolean isAnalyticsComplete()
	{
		return isAnalyticsComplete;
	}

	public void setAnalyticsComplete(boolean value)
	{
		isAnalyticsComplete = value;
	}
	public boolean isPublished()
	{
		return isPublished;
	}

	public void setPublished(boolean value)
	{
		isPublished = value;
	}

	public boolean isAllStagesCompleted()
	{
		if(this.isPostProcessingComplete && this.isEnrichedData && this.isIndexed && this.isAnalyticsComplete && this.isPublished)
		{
			return true;
		}
		return false;
		
	}


}

package automationLibrary;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GenFunc {
	
	public static int StringToInt(String digit)
	{
		int value;
		try
		{
		value = Integer.parseInt(digit);
		}
		catch(Exception e)
		{
			new Exception("unable to parse the value into integer.");
			return 0;
		}
		return value;
	}
	
	public static Date StringToDate(String date)
	{
		Date value = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			value = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return value;
	}
	
	public static ArrayList<String> getFilesNamesByFolder(String folderPath, ArrayList<String> filesList )
	{
		//File[] files  = new File(FolderPath).listFiles();
		File folder = new File(folderPath);
		if(folder.exists())
		{
			File[] listOfFiles = folder.listFiles();
	
			for (int i = 0; i < listOfFiles.length; i++) {
			  if (listOfFiles[i].isFile()) {
			    filesList.add(listOfFiles[i].getName());
			  } else if (listOfFiles[i].isDirectory()) {
				  getFilesNamesByFolder(folderPath+"\\"+listOfFiles[i].getName(),filesList);
			  }
			}
			
			return filesList;
		}else
		{
			new Exception("The path provided is invalid, please check and run this again.");
			return null;
		}
	}
	
	public static String getFourDigitRandomCode()
	{
		int randomCode = (int)(Math.random()*9000)+1000;

		return Integer.toString(randomCode);
	}
	
		

}

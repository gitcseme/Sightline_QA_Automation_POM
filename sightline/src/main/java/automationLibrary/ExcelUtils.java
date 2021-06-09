package automationLibrary;

import java.io.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils {
	static Workbook workbook;
	static  Sheet sheet;
	static Cell cell;
	static Row row;
	 static  FileInputStream inputStream;
	
	public static String[][] getArray(String filePath, String sheetName) throws Exception{
		String[][] tabArray = null;
		workbook = null;
		sheet = null;
		cell = null;
		row = null;		
		inputStream = null;
		
		DataFormatter formatter = new DataFormatter();
		inputStream = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(inputStream);	
		sheet = workbook.getSheet(sheetName);				
		
		int totalRows = sheet.getLastRowNum();
		int totalCols = sheet.getRow(0).getLastCellNum();
		tabArray = new String[totalRows][totalCols];
		int startRow = 1;
		for (int i=startRow;i<=totalRows;i++) {
			   for (int j=0;j<=totalCols-1;j++){
				   cell = sheet.getRow(i).getCell(j);
				   tabArray[i-1][j]=formatter.formatCellValue(cell);
					}			
		}		
		inputStream.close();
		return (tabArray);		
	}
}

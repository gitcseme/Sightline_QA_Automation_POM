package configsAndTestData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testScriptsSmoke.Input;

public class ExcelLoader {

    public void getExcelData(String filePath,String fileName,String sheetName) throws IOException{

    //Create an object of File class to open xlsx file

    //File file =    new File(filePath+"\\"+fileName);
    	File file =    new File(System.getProperty("user.dir")+"\\Misc\\"+"RedactionOffset.xlsx");
    //Create an object of FileInputStream class to read excel file

    FileInputStream inputStream = new FileInputStream(file);

    Workbook guru99Workbook = null;

    //Find the file extension by splitting file name in substring  and getting only extension name

    String fileExtensionName = fileName.substring(fileName.indexOf("."));

    //Check condition if the file is xlsx file

    if(fileExtensionName.equals(".xlsx")){

    //If it is xlsx file then create object of XSSFWorkbook class

    //guru99Workbook = new XSSFWorkbook(inputStream);

    }

    //Check condition if the file is xls file

    else if(fileExtensionName.equals(".xls")){

        //If it is xls file then create object of HSSFWorkbook class

        guru99Workbook = new HSSFWorkbook(inputStream);

    }

    //Read sheet inside the workbook by its name

    Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

    //Find number of rows in excel file
    int rowCount = guru99Sheet.getLastRowNum();
    System.out.println("Total Row" +rowCount);

   // int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();

    //Create a loop over all the rows of excel file to read it

    for (int i = 0; i < rowCount; i++) {

        Row row = guru99Sheet.getRow(i);

        //Create a loop to print cell values in a row

        for (int j = 0; j < row.getLastCellNum(); j++) {

            //Print Excel data in console
        	 String data0= new DataFormatter().formatCellValue(guru99Sheet.getRow(i).getCell(j)); 
             System.out.println("Test Data From Excel"+data0);
          //  System.out.print(row.getCell(j).getStringCellValue()+"|| ");

        }

        System.out.println();
    } 
   }
    
  //Main function is calling readExcel function to read data from excel file

    public static void main(String[] args) throws IOException{

    //Create an object of ReadGuru99ExcelFile class

    	ExcelLoader objExcelFile = new ExcelLoader();

    //Prepare the path of excel file

    String filePath = System.getProperty("user.dir")+"\\Misc\\"+"RedactionOffset.xlsx";

    //Call read file method of the class to read data

    objExcelFile.getExcelData(filePath,"RedactionOffset.xlsx","Sheet1");

    }

    }  

package utility;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


 
public class TestDataUtils {
 
	protected static XSSFWorkbook ExcelWBook; 
	protected static XSSFSheet ExcelWSheet;
	protected static XSSFCell Cell;
	protected static XSSFRow Row;
	
	protected static LinkedHashMap<String, List<Map<String, String>>> superMap;
	protected static List<Map<String, String>> testDataRow = new ArrayList<Map<String, String>>();
	protected static Map<String, String> dataMap;
	protected static LinkedHashMap<String, String> configDataMap;
	protected static Properties properties;
	protected static String propertyFilePath= "TestData//Data.properties";
	
	int headerRow;
	
	public static Properties getDataFromProperties() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));
		Properties properties = new Properties();
		properties.load(reader);
		return properties;
	}
   
    public static void setExcelFile(String Path,String SheetName) throws Exception 
    {
    	try {
    		FileInputStream ExcelFile = new FileInputStream(Path);
    		ExcelWBook = new XSSFWorkbook(ExcelFile);
    		ExcelWSheet = ExcelWBook.getSheet(SheetName);
    		}
    		catch (Exception e)
    		{
    			throw (e);
    		}
 
    }
    public static String getCellData(int RowNum, int ColNum) throws Exception
    {
    	try {
    		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
    		String CellData = Cell.getStringCellValue();
    		return CellData;
     	}
     	catch (Exception e){
     		return "";
     	}  
     }
    
    public static int getRowCount(String SheetName)
 	{
 		Sheet s=ExcelWBook.getSheet(SheetName);
 		return(s.getLastRowNum());
 	}
 	public static int getColumnCount(String SheetName,int rowNum)
 	{
 		XSSFRow s=ExcelWBook.getSheet(SheetName).getRow(rowNum);
 		return(s.getLastCellNum());
 	}
 	
    //-----------SR: to get data in collection-----------
 	public static LinkedHashMap<String, List<Map<String, String>>> getExceldata(String SheetName) throws Exception
 	{
 		XSSFRow headerRow = null;
 		ArrayList<Map<String, String>> listDataMap = null;
 		
 		superMap=new LinkedHashMap<String, List<Map<String, String>>>();
 		String ExcelPath = System.getProperty("user.dir")+"\\TestData\\TestData.xlsx";
 		setExcelFile(ExcelPath,SheetName);

	    int lastRow = ExcelWSheet.getLastRowNum();
	     
	    FormulaEvaluator evaluator=ExcelWBook.getCreationHelper().createFormulaEvaluator();
	   
	    int n=0;
	     
	    // Looping over entire row
	    for (int i = 1; i <= lastRow; i++)
	    {
                         
	    	Row = ExcelWSheet.getRow(i);
            int colNum = Row.getLastCellNum();
            
            String keycell = Row.getCell(0).getStringCellValue();
            
            if(!(keycell.startsWith("Test Scenario Name") || keycell.equalsIgnoreCase("Test Scenario")))
            {
            	n++;
                  
                headerRow = ExcelWSheet.getRow(i-n);
            }
            else
            {
            	headerRow = ExcelWSheet.getRow(i);
                if(headerRow.getCell(0).getStringCellValue().startsWith("Test Scenario Name")) 
                {
                	n=0;
                    listDataMap=new ArrayList<Map<String, String>>();
                }            
            }
            
            dataMap = new LinkedHashMap<String, String>();
            for (int j = 0; j < colNum; j++) 
            {
            	String InnerMapvalue ="";
                  
                evaluator.evaluateInCell(Row.getCell(j));
                switch(Row.getCell(j).getCellType())
                {
                	case NUMERIC:
                         InnerMapvalue=String.valueOf(Row.getCell(j).getNumericCellValue());
                        
                         break;
                	case STRING:
                         InnerMapvalue=Row.getCell(j).getStringCellValue();
                         break;
                	case BLANK:
                         break;
                	case BOOLEAN:
                         InnerMapvalue=String.valueOf(Row.getCell(j).getBooleanCellValue());
                         break;
                		
                	default:
                         break;
                } 
                dataMap.put(headerRow.getCell(j).getStringCellValue(), InnerMapvalue);
            }
            listDataMap.add(dataMap);
            superMap.put(keycell, listDataMap);
	    }
     // Returning excelFileMap
	    
     return superMap;
 	}

 	public static  List<Map<String, String>> getTestDataRow(String SheetName,String Iteration_Flag) throws Exception
 	{
 		superMap=getExceldata(SheetName);
        if(superMap.get(Iteration_Flag).toString().equalsIgnoreCase("yes"))
        {
        	testDataRow=superMap.get(Iteration_Flag);
        }
        return testDataRow;
    }

	public static Map<String,String> getConfigurationExceldata(String SheetName)  throws Exception
	{
		setExcelFile(System.getProperty("user.dir")+"\\TestData\\"+"TestData.xlsx",SheetName);
		
		int lastRow = ExcelWSheet.getLastRowNum();
		configDataMap = new LinkedHashMap<String, String>();
		for (int i = 1; i <= lastRow; i++)
		{
			Row = ExcelWSheet.getRow(i);
			configDataMap.put(Row.getCell(0).getStringCellValue(), Row.getCell(1).getStringCellValue());
		}
		return configDataMap;
	}
}
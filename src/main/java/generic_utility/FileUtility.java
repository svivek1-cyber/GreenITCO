package generic_utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileUtility {

    public String getDataFromPropertiesFile(String key) throws IOException {
        String value;
        try (FileInputStream fis = new FileInputStream("./src/test/resources/cd/commondata.properties")) 
        {
            Properties pObj = new Properties();
            pObj.load(fis);
            value = pObj.getProperty(key);
        }
        return value;
    }

    public String getDataFromExcelFile(String sheet, int row, int cell) throws IOException {
        String value;
        try (FileInputStream fis = new FileInputStream("./src/test/resources/TestScript/TestScriptData.xlsx")) 
        {
            Workbook workbook = WorkbookFactory.create(fis);
            value = workbook.getSheet(sheet)
                    .getRow(row)
                    .getCell(cell)
                    .getStringCellValue();
        }
        return value;
    }

    
}

package Service.Impl;

import Service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelServiceImpl implements ExcelService {
    @Override
    public List<List<Object>> readExcel(File file) throws IOException {
        List<List<Object>> lists = new ArrayList<>();
        try{
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            DataFormatter dataFormatter = new DataFormatter();

            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                List<Object> objectList = new ArrayList<>();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    Object object = null;
                    if (cell.getCellTypeEnum() == CellType.STRING) {
                        object = cell.getStringCellValue();
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        object = dataFormatter.formatCellValue(cell);
                    }
                    objectList.add(object);
                }
                lists.add(objectList);
            }

        }catch (FileNotFoundException f){
            f.printStackTrace();
        }
        return lists;
    }

    @Override
    public void writeExcel(String fileName, List<List<Object>> lists) {
        String finalFileName = "src\\main\\resources\\doc" + "\\" + fileName + ".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        int rowNum = 0;

        for (List<Object> listObject: lists) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object object: listObject) {
                Cell cell = row.createCell(colNum++);
                if (object instanceof String) {
                    cell.setCellValue((String) object);
                } else if (object instanceof Integer) {
                    cell.setCellValue((Integer) object);
                }
            }
        }

        try{
            FileOutputStream outputStream = new FileOutputStream(new File(finalFileName));
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ExcelServiceImpl excelService = new ExcelServiceImpl();
        File file = new File("src\\main\\resources\\doc\\input1.xlsx");
        List<List<Object>> lists = excelService.readExcel(file);

        for (List<Object> list: lists) {
            for ( Object object: list) {
                System.out.print(object.toString() +"\t\t");
            }
            System.out.println();
        }

        DataServiceImpl dataService = new DataServiceImpl();
        List<List<Object>> listData = dataService.listAll(lists);
        List<List<Object>> listFilter = listData
                .stream()
                .filter(list -> Integer.parseInt(list.get(1).toString()) > 20)
                .collect(Collectors.toList());
        excelService.writeExcel("output1",listFilter);
    }
}

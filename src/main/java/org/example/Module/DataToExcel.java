package org.example.Module;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Entity.LotteryResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DataToExcel {
    public static void saveToFile(List<LotteryResult> resultList, String dateNow, String location) throws IOException {
        try {
            String excelFilePath = location + "\\XSKT_" + dateNow + ".xlsx";
            Workbook workbook = getWorkbook(excelFilePath);
            Sheet sheet = workbook.getSheetAt(0);

            int lastRowIndex = sheet.getLastRowNum();

            for (LotteryResult lotteryResult : resultList) {
                lastRowIndex++;
                Row rowToInsert = sheet.getRow(lastRowIndex);
                if (rowToInsert == null) rowToInsert = sheet.createRow(lastRowIndex);

                Field[] fields = lotteryResult.getClass().getDeclaredFields();
                for (int i = 0; i < 5; i++) {
                    Cell cell = rowToInsert.createCell(i);
                    Field field = fields[i];
                    field.setAccessible(true);
                    try {
                        cell.setCellValue(field.get(lotteryResult).toString());
                    } catch (NullPointerException e) {
                        cell.setCellValue("");
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        File file = new File(excelFilePath);
        boolean fileExists = file.exists();

        if (!fileExists) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data sheet");

            Row headerRow = sheet.createRow(0);
            String[] fieldNames = {"Region", "Province", "Date", "Prize", "WinningNumber"};
            for (int i = 0; i < 5; i++)
                headerRow.createCell(i).setCellValue(fieldNames[i]);
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }

        FileInputStream inputStream = new FileInputStream(excelFilePath);
        return new XSSFWorkbook(inputStream);
    }

}

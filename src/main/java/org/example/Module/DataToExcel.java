package org.example.Module;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Database.DBConnect;
import org.example.Entity.LotteryResult;
import org.example.Mail.ErrorEmailSender;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DataToExcel {
    public static void saveToFile(List<LotteryResult> resultList, String dateNow, String location,int id, Connection connection) throws IOException, SQLException {
        try {
            //(Crawl) 7.5. Đặt tên file là location + "XSKT_" + date + ".xlxs"
            String excelFilePath = location + "\\XSKT_" + dateNow + ".xlsx";
            //(Crawl) 7.6, 7.7, 7.8, 7.9, 7.10
            Workbook workbook = getWorkbook(excelFilePath);
            Sheet sheet = workbook.getSheetAt(0);

            int lastRowIndex = sheet.getLastRowNum();
            //(Crawl) 7.11. Lấy từng dòng dữ liệu LotteryResult trong list để đưa vào file excel
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
        } catch (Exception e) {
            String date = LocalDate.now().toString();
            //(Crawl) 8.7. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to crawl data: " + e, date);
            //(Crawl) 8.8. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Crawler", "Fail " + e);
            //(Crawl) 8.9. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        File file = new File(excelFilePath);
        boolean fileExists = file.exists();
        if (!fileExists) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data sheet");

            Row headerRow = sheet.createRow(0);
            String[] fieldNames = {"Mien", "Dai", "Ngay", "tenGiai", "soTrungThuong"};
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

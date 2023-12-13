package org.example.Module;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Database.DBConnect;
import org.example.Mail.ErrorEmailSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class ExcelToDatabaseStaging {
    public static void  extractToStaging(String pathFile, Connection connection, int id, String date1) throws SQLException {
        try (FileInputStream excelFile = new FileInputStream(pathFile); Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                String region = currentRow.getCell(0).getStringCellValue();
                String province = currentRow.getCell(1).getStringCellValue();
                String date = currentRow.getCell(2).getStringCellValue();
                String prize = currentRow.getCell(3).getStringCellValue();
                String winning_number = currentRow.getCell(4).getStringCellValue();

                CallableStatement callableStatement = connection.prepareCall("{call ExtractToStaging(?, ?, ?, ?, ?)}");
                callableStatement.setString(1, region);
                callableStatement.setString(2, province);
                callableStatement.setString(3, date);
                callableStatement.setString(4, prize);
                callableStatement.setString(5, winning_number);

                callableStatement.execute();
            }
            System.out.println("success!");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DBConnect.insertErrorStatus(connection, id, "ERROR", "Fail to load to staging" + e, date1);
            DBConnect.getConnection().close();
        }
    }


    public static Optional<File> findLatestExcelFile(String folderPath) throws IOException {
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder) || !Files.isDirectory(folder))
            return Optional.empty();

        try (Stream<Path> walk = Files.walk(folder)) {
            return walk
                    .filter(path -> path.toString().toLowerCase().endsWith(".xlsx") || path.toString().toLowerCase().endsWith(".xls"))
                    .map(Path::toFile)
                    .max(Comparator.comparingLong(File::lastModified));
        }
    }

    public static void startLoadToStaging(int id, Connection connection, String location, String date) throws SQLException {
        DBConnect.insertStatus(connection, id, "EXTRACTING", date);
        try (CallableStatement callableStatement = connection.prepareCall("TRUNCATE staging.lottery_result_staging")) {
            callableStatement.execute();
            Optional<File> latestExcelFile = findLatestExcelFile(location);
            if (latestExcelFile.isPresent()) {
                File excelFile = latestExcelFile.get();
                extractToStaging(excelFile.getAbsolutePath(), connection, id, date);
                DBConnect.insertStatus(connection, id, "EXTRACTED", date);
            } else {
                DBConnect.insertErrorStatus(connection, id, "ERROR", "Fail to load to staging", date);
                ErrorEmailSender.sendMail("Extract to staging", "Can't not get file");
                DBConnect.getConnection().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to staging: " + e, date);
            ErrorEmailSender.sendMail("Extract to staging", "Fail " + e);
            DBConnect.getConnection().close();
        } catch (SQLException e) {
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to staging: " + e, date);
            ErrorEmailSender.sendMail("Extract to staging", "Fail " + e);
            DBConnect.getConnection().close();
            throw new RuntimeException(e);
        }
    }
}

package org.example.Module;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Database.DBConnect;
import org.example.Database.DBProperties;
import org.example.Entity.DataFileConfig;
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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
            System.out.println("Extract successfully!");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            //(ExtractToStaging) 9.7. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id, "ERROR", "Fail " + e, date1);
            //(ExtractToStaging) 9.8. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Extract to staging", "Fail" + e);
            //(ExtractToStaging) 9.9. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }


    public static Optional<File> findLatestExcel(String folderPath) throws IOException {
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

    public static void startExtract(int id, Connection connection, String location, String date) throws SQLException {
        //(ExtractToStaging) 9.1. insert vào data_files với status = EXTRACTING
        DBConnect.insertStatus(connection, id, "EXTRACTING", date);
        //(ExtractToStaging) 9.2. Truncate bảng staging.lottery_result_staging
        try (CallableStatement callableStatement = connection.prepareCall("TRUNCATE staging.lottery_result_staging")) {
            callableStatement.execute();
            //(ExtractToStaging) 9.3. Tìm file excel với đường dẫn là location có thời gian sữa chữa gần đây nhất
            Optional<File> latestExcelFile = findLatestExcel(location);
            //(ExtractToStaging) 9.4. Có file
            if (latestExcelFile.isPresent()) {
                File excelFile = latestExcelFile.get();
                //(ExtractToStaging) 9.5. Lấy từng dòng dữ liệu trong excel để insert vào staging
                extractToStaging(excelFile.getAbsolutePath(), connection, id, date);
                //(ExtractToStaging) 9.6. insert vào data_files với status = EXTRACTED
                DBConnect.insertStatus(connection, id, "EXTRACTED", date);
            } else {
                //(ExtractToStaging) 9.7. insert vào data_files với status = ERROR và note là lỗi của nó
                DBConnect.insertErrorStatus(connection, id, "ERROR", "Fail file not found", date);
                //(ExtractToStaging) 9.8. Gửi mail báo lỗi
                ErrorEmailSender.sendMail("Extract to staging", "file not found");
                //(ExtractToStaging) 9.9. Đóng connection database control
                DBConnect.getConnection().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //(ExtractToStaging) 9.7. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to extract to staging: " + e, date);
            //(ExtractToStaging) 9.8. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Extract to staging", "Fail " + e);
            //(ExtractToStaging) 9.9. Đóng connection database control
            DBConnect.getConnection().close();
        } catch (SQLException e) {
            //(ExtractToStaging) 9.7. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to extract to staging: " + e, date);
            //(ExtractToStaging) 9.8. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Extract to staging", "Fail " + e);
            //(ExtractToStaging) 9.9. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnect.getConnection();
        List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
        String run = DBProperties.getRun();
        LocalDate dateNow = LocalDate.now();
        String date = (run.equals("auto") ? dateNow.getYear() + "-" + (dateNow.getMonthValue() < 10 ? "0" + dateNow.getMonthValue() : dateNow.getMonthValue()) + "-" + (dateNow.getDayOfMonth() < 10 ? "0" + dateNow.getDayOfMonth() : dateNow.getDayOfMonth()) : run);
        for (DataFileConfig config : configs) {
            String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
            if(status.equals("EXTRACTING") || status.equals("CRAWLED")) {
                startExtract(config.getId(), connection, config.getLocation(), date);
            }
           else {
                if (status.equals("EXTRACTED")) {
                    System.out.println("Data has been extracted!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been extracted!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }

    }
}

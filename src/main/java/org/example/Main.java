package org.example;

import org.example.Database.DBConnect;
import org.example.Database.DBProperties;
import org.example.Entity.DataFileConfig;
import org.example.Mail.ErrorEmailSender;
import org.example.Module.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //(MainFlow) 1. Kết nối DB Control
        try (Connection connection = DBConnect.getConnection()) {
            //(MainFlow) 2. Kết nối thành công
            //(MainFlow) 3. Kết nối vào config.propertíes để lấy giá trị run là auto(ngày hiện tại) hay là ngày khác
            LocalDate dateNow = LocalDate.now();
            String run = DBProperties.getRun();
            String date = (run.equals("auto") ? dateNow.getYear() + "-" + (dateNow.getMonthValue() < 10 ? "0" + dateNow.getMonthValue() : dateNow.getMonthValue()) + "-" + (dateNow.getDayOfMonth() < 10 ? "0" + dateNow.getDayOfMonth() : dateNow.getDayOfMonth()) : run);
            System.out.println(date);
            //(MainFlow) 4. Lấy tất cả dòng có flag = 1 trong bảng data_file_configs
            List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
            // (MainFlow) 5. Lấy 1 dòng join với bảng data_files & (MainFlow) 13. Lấy dòng tiếp theo có flag=1 trong bảng data_file_configs
            for (DataFileConfig config : configs) {
                // (MainFlow) 6. Lấy ra status của dòng có file_timestamp mới nhất ngoại trừ status = ERROR
                String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
                switch (status) {
                    // (MainFlow) 7. status = FINISH && (MainFlow) 17. status = CRAWLING
                    case "FINISHED", "CRAWLING" -> {
                        // (MainFlow) 8. Tiến hành Crawl data từ source
                        CrawlData.startCrawl(config.getSource_path(), config.getLocation(), config.getId(), connection, date, run);
                        // (MainFlow) 9. Tiến hành extract file excel vào staging
                        ExcelToDatabaseStaging.startExtract(config.getId(), connection, config.getLocation(), date);
                        // (MainFlow) 10. Tiến hành transform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 13. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 18. status = EXTRACTING
                    case "EXTRACTING" -> {
                        // (MainFlow) 9. Tiến hành extract file excel vào staging
                        ExcelToDatabaseStaging.startExtract(config.getId(), connection, config.getLocation(), date);
                        // (MainFlow) 10. Tiến hành transform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 13. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 19. status = TRANSFORMING
                    case "TRANSFORMING" -> {
                        // (MainFlow) 10. Tiến hành transform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 13. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 20. status = LOADINGWH
                    case "LOADINGFACT" -> {
                        // (MainFlow) 10. Tiến hành transform dữ liệu
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 21. status = AGREGATING
                    case "AGGREGATING" -> {
                        // (MainFlow) 12. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 13. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 22. status = LOADINGM
                    case "LOADINGM" ->
                        // (MainFlow) 13. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    default -> {
                    }
                }
                // (MainFlow) 15. Đóng connection database control
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // (MainFlow) 23. Gửi mail thông báo lỗi
            ErrorEmailSender.sendMail("Can not connect to database", "Fail " + e);
        }
    }
}
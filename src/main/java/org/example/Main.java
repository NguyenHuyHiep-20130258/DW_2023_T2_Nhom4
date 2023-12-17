package org.example;

import org.example.Database.DBConnect;
import org.example.Entity.DataFileConfig;
import org.example.Mail.ErrorEmailSender;
import org.example.Module.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //(MainFlow) 1. Kết nối DB Control
        try (Connection connection = DBConnect.getConnection()) {
            //(MainFlow) 2. Kết nối thành công
            String date = LocalDate.now().toString();
            //(MainFlow) 3. Lấy tất cả dòng có flag = 1 trong bảng data_file_configs
            List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
            // (MainFlow) 4. Lấy 1 dòng join với bảng data_files & (MainFlow) 13. Lấy dòng tiếp theo có flag=1 trong bảng data_file_configs
            for (DataFileConfig config : configs) {
                // (MainFlow) 5. Lấy ra status của dòng có file_timestamp mới nhất ngoại trừ status = ERROR
                String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
                switch (status) {
                    // (MainFlow) 6. status = FINISH && (MainFlow) 16. status = CRAWLING
                    case "FINISHED", "CRAWLING" -> {
                        // (MainFlow) 7. Tiến hành Crawl data từ source
                        CrawlData.startCrawl(config.getSource_path(), config.getLocation(), config.getId(), connection, date);
                        // (MainFlow) 8. Tiến hành extract file excel vào staging
                        ExcelToDatabaseStaging.startExtractToStaging(config.getId(), connection, config.getLocation(), date);
                        // (MainFlow) 9. Tiến hành tranform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 10. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 17. status = EXTRACTING
                    case "EXTRACTING" -> {
                        // (MainFlow) 8. Tiến hành extract file excel vào staging
                        ExcelToDatabaseStaging.startExtractToStaging(config.getId(), connection, config.getLocation(), date);
                        // (MainFlow) 9. Tiến hành tranform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 10. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 18. status = TRANSFORMING
                    case "TRANSFORMING" -> {
                        // (MainFlow) 9. Tiến hành tranform dữ liệu
                        Transform.Transform(config.getId(), connection, date);
                        // (MainFlow) 10. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 19. status = LOADINGWH
                    case "LOADINGFACT" -> {
                        // (MainFlow) 10. Tiến hành đổ dữ liệu vào bảng fact
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 20. status = AGREGATING
                    case "AGGREGATING" -> {
                        // (MainFlow) 11. Tiến hành tổng hợp, rút gọn dữ liệu
                        AggregateData.aggregateData(config.getId(), connection, date);
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    // (MainFlow) 21. status = LOADINGM
                    case "LOADINGM" ->
                        // (MainFlow) 12. Tiến hành đổ dữ liệu vào mart
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    default -> {
                    }
                }
                // (MainFlow) 15. Đóng connection database control
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // (MainFlow) 22. Gửi mail thông báo lỗi
            ErrorEmailSender.sendMail("Can not connect to database", "Fail " + e);
        }
    }
}
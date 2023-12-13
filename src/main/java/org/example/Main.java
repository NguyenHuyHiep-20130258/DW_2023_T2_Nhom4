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
        try (Connection connection = DBConnect.getConnection()) {
            String date = LocalDate.now().toString();
            List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
            for (DataFileConfig config : configs) {
                String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
                switch (status) {
                    case "FINISHED", "CRAWLING" -> {
                        CrawlData.startCrawl(config.getSource_path(), config.getLocation(), config.getId(), connection, date);
                        ExcelToDatabaseStaging.startLoadToStaging(config.getId(), connection, config.getLocation(), date);
                        TransformDimToFact.Transform(config.getId(), connection, date);
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        AggregateData.aggregateData(config.getId(), connection, date);
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    case "EXTRACTING" -> {
                        ExcelToDatabaseStaging.startLoadToStaging(config.getId(), connection, config.getLocation(), date);
                        TransformDimToFact.Transform(config.getId(), connection, date);
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        AggregateData.aggregateData(config.getId(), connection, date);
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    case "TRANSFORMING" -> {
                        TransformDimToFact.Transform(config.getId(), connection, date);
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        AggregateData.aggregateData(config.getId(), connection, date);
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    case "LOADINGFACT" -> {
                        LoadToWareHouse.LoadtoWareHouse(config.getId(), connection, date);
                        AggregateData.aggregateData(config.getId(), connection, date);
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    case "AGGREGATING" -> {
                        AggregateData.aggregateData(config.getId(), connection, date);
                        LoadToMart.LoadToMart(config.getId(), connection, date);
                    }
                    case "LOADINGM" -> LoadToMart.LoadToMart(config.getId(), connection, date);
                    default -> {
                    }
                }
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ErrorEmailSender.sendMail("Can not connect to database", "Fail " + e);
        }
    }
}
package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Entity.LotteryResult;
import org.example.Mail.ErrorEmailSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrawlData {
    static final String[] regions = {"xo-so-mien-trung/xsmt-p1.html", "xo-so-mien-nam/xsmn-p1.html",  "xo-so-mien-bac/xsmb-p1.html"};
    public static void crawl(String source_path, String location, String region, int id, Connection connection) throws SQLException {
        try {
            Document document;
            document = Jsoup.connect(source_path + region).userAgent("Mozilla/5.0").get();
            LocalDate date = LocalDate.now();
            List<LotteryResult> list = new ArrayList<LotteryResult>();
            String dateNow = date.getYear() + "-" + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue()) + "-" + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
            String substring = region.substring(region.indexOf("xs") + 2, region.indexOf("xs") + 4);
            System.out.println(substring);
             Element table = document.getElementsByClass("section").get(1).select("table:first-child").get(0);
            int i = 2;
            if (substring.equals("mb")) {
                String province = document.select(".section-header .site-link").get(0).text();
                String provinceName = province.substring(province.indexOf("(") + 1, province.indexOf(")"));
                for (int j = 2; j < 10; j++) {
                    assert table != null;
                    Elements numbers = table.select("tbody tr:nth-child(" + j + ") td:nth-child(" + i + ") span");
                    String prize = "Giải " + table.select("tbody tr:nth-child(" + j + ") td:first-child").get(0).text();
                    for (Element number : numbers) {
                        LotteryResult result = new LotteryResult("Miền Bắc", provinceName, dateNow, prize, number.text());
                        list.add(result);
                        System.out.println(result);
                    }
                }
            } else {
                assert table != null;
                for (Element e : table.select("thead tr th:not(:first-child)")) {
                    String provinceName = e.text();
                    for (int j = 1; j < 10; j++) {
                        String prize = "Giải " + table.select("tbody tr:nth-child(" + j + ") th").get(0).text();
                        Elements numbers = table.select("tbody tr:nth-child(" + j + ") td:nth-child(" + i + ") span");
                        for (Element number : numbers) {
                            if(substring.equals("mt")) {
                                LotteryResult result = new LotteryResult("Miền Trung", provinceName, dateNow, prize, number.text());
                                list.add(result);
                                System.out.println(result);
                            }
                            else {
                                LotteryResult result = new LotteryResult("Miền Nam", provinceName, dateNow, prize, number.text());
                                list.add(result);
                                System.out.println(result);
                            }

                        }
                    }
                    i++;
                }
            }
            DataToExcel.saveToFile(list, dateNow, location);
        } catch (IOException e) {
            String date = LocalDate.now().toString();
            e.printStackTrace();
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to crawl data: " + e, date);
            ErrorEmailSender.sendMail("Crawler", "Fail " + e);
            DBConnect.getConnection().close();
        }
    }

    public static void startCrawl(String source_path, String location, int id, Connection connection, String date) throws SQLException {
        DBConnect.insertStatus(connection, id, "CRAWLING", date);
        for (String r : regions)
            crawl(source_path, location, r, id, connection);
        DBConnect.insertStatus(connection, id, "CRAWLED", date);
    }

    public static void main(String[] args) throws SQLException {
        String date = LocalDate.now().toString();
        Connection connection = DBConnect.getConnection();
        startCrawl("https://xoso.com.vn/2", "E:\\Warehouse", 1, connection, date);
    }
}

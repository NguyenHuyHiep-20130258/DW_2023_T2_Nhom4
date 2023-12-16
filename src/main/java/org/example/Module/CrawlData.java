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
            //(Crawl) 7.3. Kết nối đến trang web từ đường dẫn là cột source_path  trong bảng data_file_configs và địa chỉ miền
            document = Jsoup.connect(source_path + region).userAgent("Mozilla/5.0").get();
            LocalDate date = LocalDate.now();
            List<LotteryResult> list = new ArrayList<LotteryResult>();
            String dateNow = date.getYear() + "-" + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue()) + "-" + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
            String substring = region.substring(region.indexOf("xs") + 2, region.indexOf("xs") + 4);
            System.out.println(substring);
             Element table = document.getElementsByClass("section").get(1).select("table:first-child").get(0);
            int i = 2;
            //(Crawl) 7.4. Lấy dữ liệu bằng script và lưu các dòng dữ liệu vào List <LotteryResult>
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
                            }
                            else {
                                LotteryResult result = new LotteryResult("Miền Nam", provinceName, dateNow, prize, number.text());
                                list.add(result);
                            }

                        }
                    }
                    i++;
                }
            }
            //(Crawl) 7.5, 7.6 trong model DataToExcel
            DataToExcel.saveToFile(list, dateNow, location,id, connection);
        } catch (IOException e) {
            String date = LocalDate.now().toString();
            e.printStackTrace();
            //(Crawl) 7.8. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to crawl data: " + e, date);
            //(Crawl) 7.9. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Crawler", "Fail " + e);
            //(Crawl) 7.10. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }

    public static void startCrawl(String source_path, String location, int id, Connection connection, String date) throws SQLException {
        //(Crawl) 7.1. insert vào data_files với status = CRAWLING
        DBConnect.insertStatus(connection, id, "CRAWLING", date);
        //(Crawl) 7.2. Lấy lần lượt từng địa chỉ từng miền của trang web
        for (String r : regions)
            //(Crawl) 7.3, 7.4, 7.5, 7.6
            crawl(source_path, location, r, id, connection);
        System.out.println("Crawl data successfully!");
        //(Crawl) 7.7. insert vào data_files với status = CRAWLED
        DBConnect.insertStatus(connection, id, "CRAWLED", date);
    }

    public static void main(String[] args) throws SQLException {
        String date = LocalDate.now().toString();
        Connection connection = DBConnect.getConnection();
        startCrawl("https://xoso.com.vn/2", "E:\\Warehouse", 1, connection, date);
    }
}

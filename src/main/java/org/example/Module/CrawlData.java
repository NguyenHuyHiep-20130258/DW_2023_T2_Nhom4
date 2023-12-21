package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Database.DBProperties;
import org.example.Entity.DataFileConfig;
import org.example.Entity.LotteryResult;
import org.example.Mail.ErrorEmailSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrawlData {
    static final String[] regions = {"xo-so-mien-trung/xsmt-p1.html", "xo-so-mien-nam/xsmn-p1.html", "xo-so-mien-bac/xsmb-p1.html"};
    static final String[] regions_date = {"xsmn", "xsmt", "xsmb"};

    public static void crawl(String source_path, String location, String region, int id, Connection connection, String run) throws SQLException {
        try {
            Document document;
            document = Jsoup.connect(source_path + (run.equals("auto") ? region : region + "-" + run + ".html")).userAgent("Mozilla/5.0").get();
            LocalDate date = LocalDate.now();
            List<LotteryResult> list = new ArrayList<LotteryResult>();
            String dateNow;
            String currentResultDate;
            String substring = region.substring(region.indexOf("xs") + 2, region.indexOf("xs") + 4);
            String path = !substring.equals("mb") ? substring + "_kqngay_" : "kqngay_";
            if (run.equals("auto")) {
                dateNow = date.getYear() + "-" + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue()) + "-" + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth());
                currentResultDate = path + (date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth()) + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue()) + date.getYear() + "_kq";
            } else {
                String[] splited = run.split("-");
                dateNow = splited[2] + "-" + splited[1] + "-" + splited[0];
                currentResultDate = path + run.replace("-", "") + "_kq";
            }
            Element table = document.getElementById(currentResultDate).select("#" + currentResultDate + " table:first-child").get(0);
            int i = 2;
            if (substring.equals("mb") && table != null) {
                String provinceTemp = document.select(".section-header .site-link").get(0).text();
                String provinceName;
                if (run.equals("auto"))
                    provinceName = provinceTemp.substring(provinceTemp.indexOf("(") + 1, provinceTemp.indexOf(")"));
                else {
                    int lastDigitIndex = -1;
                    for (int k = provinceTemp.length() - 1; k >= 0; k--) {
                        char c = provinceTemp.charAt(k);
                        if (Character.isDigit(c)) {
                            lastDigitIndex = k;
                            break;
                        }
                    }
                    provinceName = provinceTemp.substring(lastDigitIndex + 2);
                }
                for (int j = 2; j < 10; j++) {
                    Elements numbers = table.select("tbody tr:nth-child(" + j + ") td:nth-child(" + i + ") span");
                    String prize = "Giải " + (run.equals("auto") ? table.select("tbody tr:nth-child(" + j + ") td:first-child").get(0).text() : table.select("tbody tr:nth-child(" + j + ") th:first-child").get(0).text());
                    for (Element number : numbers) {
                        LotteryResult result = new LotteryResult("Miền Bắc", provinceName, dateNow, prize, number.text());
                        list.add(result);
                    }
                }
            } else {
                if (table != null) {
                    for (Element e : table.select("thead tr th:not(:first-child)")) {
                        String provinceName = e.text();
                        for (int j = 1; j < 10; j++) {
                            String prize = "Giải " + table.select("tbody tr:nth-child(" + j + ") th").get(0).text();
                            Elements numbers = table.select("tbody tr:nth-child(" + j + ") td:nth-child(" + i + ") span");
                            for (Element number : numbers) {
                                if (substring.equals("mt")) {
                                    LotteryResult result = new LotteryResult("Miền Trung", provinceName, dateNow, prize, number.text());
                                    list.add(result);
                                } else {
                                    LotteryResult result = new LotteryResult("Miền Nam", provinceName, dateNow, prize, number.text());
                                    list.add(result);
                                }
                            }
                        }
                        i++;
                    }
                }
            }
            DataToExcel.saveToFile(list, dateNow, location, id, connection);
        } catch (Exception e) {
            String date = LocalDate.now().toString();
            e.printStackTrace();
            //(Crawl) 8.7. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id, "ERROR", "Fail to crawl data: " + e, date);
            //(Crawl) 8.8. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Crawler", "Fail " + e);
            //(Crawl) 8.9. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }

    public static void startCrawl(String source_path, String location, int id, Connection connection, String date1, String run) throws SQLException {
        String dateNow;
        //(Crawl) 8.1. insert vào data_files với status = CRAWLING
        DBConnect.insertStatus(connection, id, "CRAWLING", date1);
        //(Crawl) 8.2. Lấy ngày từ run trong config (auto thì lấy ngày hiện tại)
        if (run.equals("auto")) {
            dateNow = date1;
        } else {
            String[] split = run.split("-");
            dateNow = split[2] + "-" + split[1] + "-" + split[0];
        }
        //(Crawl) 8.3 Kiểm tra file excel tại ngày đó có tồn tại không
        File excel = new File(location + "\\XSKT_" + dateNow + ".xlsx");
        //(Crawl) 8.4. Xóa file đó
        if (excel.exists()) {
            excel.delete();
        }
        //(Crawl) 8.5. Lấy source path, địa chỉ miền tùy thuộc vào run, chạy và lưu các kết quả xổ số vào file excel
        for (String s : run.equals("auto") ? regions : regions_date) {
            crawl(source_path, location, s, id, connection, run);
        }
        System.out.println("Crawl data successfully!");
        //(Crawl)  8.6. insert vào data_files với status = CRAWLED
        DBConnect.insertStatus(connection, id, "CRAWLED", date1);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnect.getConnection();
        List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
        String run = DBProperties.getRun();
        LocalDate dateNow = LocalDate.now();
        String date = (run.equals("auto") ? dateNow.getYear() + "-" + (dateNow.getMonthValue() < 10 ? "0" + dateNow.getMonthValue() : dateNow.getMonthValue()) + "-" + (dateNow.getDayOfMonth() < 10 ? "0" + dateNow.getDayOfMonth() : dateNow.getDayOfMonth()) : run);
        for (DataFileConfig config : configs) {
            String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
            if (status.equals("CRAWLING") || status.equals("FINISHED")) {
                startCrawl(config.getSource_path(), config.getLocation(), config.getId(), connection, date, run);
            }
            else {
                if (status.equals("CRAWLED")) {
                    System.out.println("Data has been crawl!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been crawl!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }

    }
}

package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        String newDate = dateFormat.format(now);
        ArrayList<String> regions = new ArrayList<>();
        regions.add("xsmn");
        regions.add("xsmt");
        regions.add("xsmb");
        System.out.println(crawlerData(regions));

        try {
            ArrayList<Lottery> lotteryList = crawlerData(regions);
            writeLotteryDataToExcel(lotteryList, "E:\\Warehouse\\XSKT_"+ newDate +".xlsx");
            System.out.println("Excel file has been generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Lottery> crawlerData(ArrayList<String> regions) throws IOException {
        ArrayList<Lottery> list = new ArrayList<>();
        for (String region : regions) {
            String url = "https://xskt.com.vn/" + region;
            String subString = url.substring(20);
            try {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
                String newDate = dateFormat.format(now);
                Document document = Jsoup.connect(url).get();
                Element center_content = document.select("#center-content").first();
                Element box_ket_qua = center_content.select(".box-ketqua").first();

                Element region_name = box_ket_qua.select("h2 a").first();
                String region_text = region_name.text().substring(6);
                System.out.println(region_text);

                if (subString.equals("xsmn")) {
                    Elements row = box_ket_qua.select("tbody tr");
                    Elements province = row.get(0).select("th");
                    Elements giai_tam = row.get(1).select("td");
                    Elements giai_bay = row.get(2).select("td");
                    Elements giai_sau = row.get(3).select("td");
                    Elements giai_nam = row.get(4).select("td");
                    Elements giai_tu = row.get(5).select("td");
                    Elements giai_ba = row.get(6).select("td");
                    Elements giai_nhi = row.get(7).select("td");
                    Elements giai_nhat = row.get(8).select("td");
                    Elements giai_db = row.get(9).select("td");
                    for (int i = 1; i < 4; i++) {
                        String province1_text = province.get(i).text();
                        System.out.println(province1_text);
                        String giai_tam1_text = giai_tam.get(i).text();
                        System.out.println(giai_tam1_text);
                        String giai_bay1_text = giai_bay.get(i).text();
                        System.out.println(giai_bay1_text);
                        String giai_sau1_text = giai_sau.get(i).text();
                        System.out.println(giai_sau1_text);
                        String giai_nam1_text = giai_nam.get(i).text();
                        System.out.println(giai_nam1_text);
                        String giai_tu1_text = giai_tu.get(i).text();
                        System.out.println(giai_tu1_text);
                        String giai_ba1_text = giai_ba.get(i).text();
                        System.out.println(giai_ba1_text);
                        String giai_nhi1_text = giai_nhi.get(i).text();
                        System.out.println(giai_nhi1_text);
                        String giai_nhat1_text = giai_nhat.get(i).text();
                        System.out.println(giai_nhat1_text);
                        String giai_db1_text = giai_db.get(i).text();
                        System.out.println(giai_db1_text);
                        if (i == 1) {
                            Lottery lottery1 = new Lottery(region_text, province1_text, newDate, "Giải tám", giai_tam1_text);
                            Lottery lottery2 = new Lottery(region_text, province1_text, newDate, "Giải bảy", giai_bay1_text);
                            Lottery lottery5 = new Lottery(region_text, province1_text, newDate, "Giải năm", giai_nam1_text);
                            Lottery lottery7 = new Lottery(region_text, province1_text, newDate, "Giải nhì", giai_nhi1_text);
                            Lottery lottery8 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                            Lottery lottery9 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                            list.add(lottery1);
                            list.add(lottery2);
                            String[] winningNumbersArray3 = giai_sau1_text.split("\\s+");
                            for (String winningNumber3 : winningNumbersArray3) {
                                Lottery lottery6 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber3);
                                list.add(lottery6);
                            }
                            list.add(lottery5);
                            String[] winningNumbersArray2 = giai_tu1_text.split("\\s+");
                            for (String winningNumber2 : winningNumbersArray2) {
                                Lottery lottery4 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber2);
                                list.add(lottery4);
                            }
                            String[] winningNumbersArray1 = giai_ba1_text.split("\\s+");
                            for (String winningNumber1 : winningNumbersArray1) {
                                Lottery lottery3 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber1);
                                list.add(lottery3);
                            }
                            list.add(lottery7);
                            list.add(lottery8);
                            list.add(lottery9);
                        }
                        if (i == 2) {
                            Lottery lottery11 = new Lottery(region_text, province1_text, newDate, "Giải tám", giai_tam1_text);
                            Lottery lottery12 = new Lottery(region_text, province1_text, newDate, "Giải bảy", giai_bay1_text);
                            Lottery lottery15 = new Lottery(region_text, province1_text, newDate, "Giải năm", giai_nam1_text);
                            Lottery lottery17 = new Lottery(region_text, province1_text, newDate, "Giải nhì", giai_nhi1_text);
                            Lottery lottery18 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                            Lottery lottery19 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                            list.add(lottery11);
                            list.add(lottery12);
                            String[] winningNumbersArray13 = giai_sau1_text.split("\\s+");
                            for (String winningNumber13 : winningNumbersArray13) {
                                Lottery lottery16 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber13);
                                list.add(lottery16);
                            }
                            list.add(lottery15);
                            String[] winningNumbersArray12 = giai_tu1_text.split("\\s+");
                            for (String winningNumber12 : winningNumbersArray12) {
                                Lottery lottery14 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber12);
                                list.add(lottery14);
                            }
                            String[] winningNumbersArray11 = giai_ba1_text.split("\\s+");
                            for (String winningNumber11 : winningNumbersArray11) {
                                Lottery lottery13 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber11);
                                list.add(lottery13);
                            }
                            list.add(lottery17);
                            list.add(lottery18);
                            list.add(lottery19);
                        }
                        if (i == 3) {
                            Lottery lottery21 = new Lottery(region_text, province1_text, newDate, "Giải tám", giai_tam1_text);
                            Lottery lottery22 = new Lottery(region_text, province1_text, newDate, "Giải bảy", giai_bay1_text);
                            Lottery lottery25 = new Lottery(region_text, province1_text, newDate, "Giải năm", giai_nam1_text);
                            Lottery lottery27 = new Lottery(region_text, province1_text, newDate, "Giải nhì", giai_nhi1_text);
                            Lottery lottery28 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                            Lottery lottery29 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                            list.add(lottery21);
                            list.add(lottery22);
                            String[] winningNumbersArray23 = giai_sau1_text.split("\\s+");
                            for (String winningNumber23 : winningNumbersArray23) {
                                Lottery lottery26 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber23);
                                list.add(lottery26);
                            }
                            list.add(lottery25);
                            String[] winningNumbersArray22 = giai_tu1_text.split("\\s+");
                            for (String winningNumber22 : winningNumbersArray22) {
                                Lottery lottery24 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber22);
                                list.add(lottery24);
                            }
                            String[] winningNumbersArray21 = giai_ba1_text.split("\\s+");
                            for (String winningNumber21 : winningNumbersArray21) {
                                Lottery lottery23 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber21);
                                list.add(lottery23);
                            }
                            list.add(lottery27);
                            list.add(lottery28);
                            list.add(lottery29);
                        }
                    }
                }
                if (subString.equals("xsmt")) {
                    Elements row = box_ket_qua.select("tbody tr");
                    Elements province = row.get(0).select("th");
                    Elements giai_tam = row.get(1).select("td");
                    Elements giai_bay = row.get(2).select("td");
                    Elements giai_sau = row.get(3).select("td");
                    Elements giai_nam = row.get(4).select("td");
                    Elements giai_tu = row.get(5).select("td");
                    Elements giai_ba = row.get(6).select("td");
                    Elements giai_nhi = row.get(7).select("td");
                    Elements giai_nhat = row.get(8).select("td");
                    Elements giai_db = row.get(9).select("td");
                    for (int i = 1; i < 3; i++) {
                        String province1_text = province.get(i).text();
                        System.out.println(province1_text);
                        String giai_tam1_text = giai_tam.get(i).text();
                        System.out.println(giai_tam1_text);
                        String giai_bay1_text = giai_bay.get(i).text();
                        System.out.println(giai_bay1_text);
                        String giai_sau1_text = giai_sau.get(i).text();
                        System.out.println(giai_sau1_text);
                        String giai_nam1_text = giai_nam.get(i).text();
                        System.out.println(giai_nam1_text);
                        String giai_tu1_text = giai_tu.get(i).text();
                        System.out.println(giai_tu1_text);
                        String giai_ba1_text = giai_ba.get(i).text();
                        System.out.println(giai_ba1_text);
                        String giai_nhi1_text = giai_nhi.get(i).text();
                        System.out.println(giai_nhi1_text);
                        String giai_nhat1_text = giai_nhat.get(i).text();
                        System.out.println(giai_nhat1_text);
                        String giai_db1_text = giai_db.get(i).text();
                        System.out.println(giai_db1_text);
                        if (i == 1) {
                            Lottery lottery1 = new Lottery(region_text, province1_text, newDate, "Giải tám", giai_tam1_text);
                            Lottery lottery2 = new Lottery(region_text, province1_text, newDate, "Giải bảy", giai_bay1_text);
                            Lottery lottery5 = new Lottery(region_text, province1_text, newDate, "Giải năm", giai_nam1_text);
                            Lottery lottery7 = new Lottery(region_text, province1_text, newDate, "Giải nhì", giai_nhi1_text);
                            Lottery lottery8 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                            Lottery lottery9 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                            list.add(lottery1);
                            list.add(lottery2);
                            String[] winningNumbersArray3 = giai_sau1_text.split("\\s+");
                            for (String winningNumber3 : winningNumbersArray3) {
                                Lottery lottery6 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber3);
                                list.add(lottery6);
                            }
                            list.add(lottery5);
                            String[] winningNumbersArray2 = giai_tu1_text.split("\\s+");
                            for (String winningNumber2 : winningNumbersArray2) {
                                Lottery lottery4 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber2);
                                list.add(lottery4);
                            }
                            String[] winningNumbersArray1 = giai_ba1_text.split("\\s+");
                            for (String winningNumber1 : winningNumbersArray1) {
                                Lottery lottery3 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber1);
                                list.add(lottery3);
                            }
                            list.add(lottery7);
                            list.add(lottery8);
                            list.add(lottery9);
                        }
                        if (i == 2) {
                            Lottery lottery11 = new Lottery(region_text, province1_text, newDate, "Giải tám", giai_tam1_text);
                            Lottery lottery12 = new Lottery(region_text, province1_text, newDate, "Giải bảy", giai_bay1_text);
                            Lottery lottery15 = new Lottery(region_text, province1_text, newDate, "Giải năm", giai_nam1_text);
                            Lottery lottery17 = new Lottery(region_text, province1_text, newDate, "Giải nhì", giai_nhi1_text);
                            Lottery lottery18 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                            Lottery lottery19 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                            list.add(lottery11);
                            list.add(lottery12);
                            String[] winningNumbersArray13 = giai_sau1_text.split("\\s+");
                            for (String winningNumber13 : winningNumbersArray13) {
                                Lottery lottery16 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber13);
                                list.add(lottery16);
                            }
                            list.add(lottery15);
                            String[] winningNumbersArray12 = giai_tu1_text.split("\\s+");
                            for (String winningNumber12 : winningNumbersArray12) {
                                Lottery lottery14 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber12);
                                list.add(lottery14);
                            }
                            String[] winningNumbersArray11 = giai_ba1_text.split("\\s+");
                            for (String winningNumber11 : winningNumbersArray11) {
                                Lottery lottery13 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber11);
                                list.add(lottery13);
                            }
                            list.add(lottery17);
                            list.add(lottery18);
                            list.add(lottery19);
                        }
                    }

                }
                if (subString.equals("xsmb")) {
                    Elements row = box_ket_qua.select("tbody tr");
                    Elements province = row.get(0).select("th");
                    Elements giai_db = row.get(1).select("td");
                    Elements giai_nhat = row.get(2).select("td");
                    Elements giai_nhi = row.get(3).select("td");
                    Elements giai_ba = row.get(4).select("td");
                    Elements giai_tu = row.get(6).select("td");
                    Elements giai_nam = row.get(7).select("td");
                    Elements giai_sau = row.get(9).select("td");
                    Elements giai_bay = row.get(10).select("td");
                    String province1_text = province.get(0).select(".h3").text().substring(13, 23);
                    System.out.println(province1_text);
                    String giai_bay1_text = giai_bay.get(1).text();
                    System.out.println(giai_bay1_text);
                    String giai_sau1_text = giai_sau.get(1).text();
                    System.out.println(giai_sau1_text);
                    String giai_nam1_text = giai_nam.get(1).text();
                    System.out.println(giai_nam1_text);
                    String giai_tu1_text = giai_tu.get(1).text();
                    System.out.println(giai_tu1_text);
                    String giai_ba1_text = giai_ba.get(1).text();
                    System.out.println(giai_ba1_text);
                    String giai_nhi1_text = giai_nhi.get(1).text();
                    System.out.println(giai_nhi1_text);
                    String giai_nhat1_text = giai_nhat.get(1).text();
                    System.out.println(giai_nhat1_text);
                    String giai_db1_text = giai_db.get(1).text();
                    System.out.println(giai_db1_text);

                    Lottery lottery9 = new Lottery(region_text, province1_text, newDate, "Giải ĐB", giai_db1_text);
                    Lottery lottery8 = new Lottery(region_text, province1_text, newDate, "Giải nhất", giai_nhat1_text);
                    list.add(lottery9);
                    list.add(lottery8);
                    String[] winningNumbersArray35 = giai_nhi1_text.split("\\s+");
                    for (String winningNumber35 : winningNumbersArray35) {
                        Lottery lottery7 = new Lottery(region_text, province1_text, newDate, "Giải nhì", winningNumber35);
                        list.add(lottery7);
                    }
                    String[] winningNumbersArray1 = giai_ba1_text.split("\\s+");
                    for (String winningNumber1 : winningNumbersArray1) {
                        Lottery lottery3 = new Lottery(region_text, province1_text, newDate, "Giải ba", winningNumber1);
                        list.add(lottery3);
                    }
                    String[] winningNumbersArray2 = giai_tu1_text.split("\\s+");
                    for (String winningNumber2 : winningNumbersArray2) {
                        Lottery lottery4 = new Lottery(region_text, province1_text, newDate, "Giải tư", winningNumber2);
                        list.add(lottery4);
                    }String[] winningNumbersArray3533 = giai_nam1_text.split("\\s+");
                    for (String winningNumber3533 : winningNumbersArray3533) {
                        Lottery lottery5 = new Lottery(region_text, province1_text, newDate, "Giải năm", winningNumber3533);
                        list.add(lottery5);
                    }
                    String[] winningNumbersArray3 = giai_sau1_text.split("\\s+");
                    for (String winningNumber3 : winningNumbersArray3) {
                        Lottery lottery6 = new Lottery(region_text, province1_text, newDate, "Giải sáu", winningNumber3);
                        list.add(lottery6);
                    }
                    String[] winningNumbersArray353 = giai_bay1_text.split("\\s+");
                    for (String winningNumber353 : winningNumbersArray353) {
                        Lottery lottery2 = new Lottery(region_text, province1_text, newDate, "Giải bảy", winningNumber353);
                        list.add(lottery2);
                    }
                }
                System.out.println("-----");
                System.out.println("\n");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void writeLotteryDataToExcel(ArrayList<Lottery> lotteryList, String outputPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Lottery Data");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Region");
        headerRow.createCell(1).setCellValue("Province");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Prize");
        headerRow.createCell(4).setCellValue("Winning Numbers");

        // Populate the sheet with lottery data
        int rowNum = 1;
        for (Lottery lottery : lotteryList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(lottery.getRegion());
            row.createCell(1).setCellValue(lottery.getProvince());
            row.createCell(2).setCellValue(lottery.getDate());
            row.createCell(3).setCellValue(lottery.getPrize());
            row.createCell(4).setCellValue(lottery.getWinning_number());
        }

        // Write the workbook content to a file
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            workbook.write(outputStream);
        }

        // Close the workbook to release resources
        workbook.close();
    }



}
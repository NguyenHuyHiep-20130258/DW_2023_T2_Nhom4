package org.example.Entity;

public class LotteryResult {
    String region;
    String province;
    String date;
    String prize;
    String winning_number;

    public LotteryResult(String region, String province, String date, String prize, String winning_number) {
        this.region = region;
        this.province = province;
        this.date = date;
        this.prize = prize;
        this.winning_number = winning_number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getWinning_number() {
        return winning_number;
    }

    public void setWinning_number(String winning_number) {
        this.winning_number = winning_number;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "region='" + region + '\'' +
                ", province='" + province + '\'' +
                ", date='" + date + '\'' +
                ", prize='" + prize + '\'' +
                ", winning_number='" + winning_number + '\'' +
                '}';
    }
}

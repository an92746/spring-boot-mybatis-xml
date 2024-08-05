package com.neo.model;

public class SelectForex {


        private String  startDate;
        private String  endDate;
        private String  currency;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "SelectForex{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}

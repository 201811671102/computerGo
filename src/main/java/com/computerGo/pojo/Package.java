package com.computerGo.pojo;

public class Package {
    private Integer pid;

    private String packagemessage;

    private Integer number;

    private Double price;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPackagemessage() {
        return packagemessage;
    }

    public void setPackagemessage(String packagemessage) {
        this.packagemessage = packagemessage == null ? null : packagemessage.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
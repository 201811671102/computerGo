package com.computerGo.pojo;

public class Package {
    private Integer pid;

    private String packagemessage;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
package com.computerGo.pojo;

public class Repertory {
    private Integer rid;

    private String title;

    private Double price;

    private Integer number;

    private Integer evaluation;

    private String photo;

    private String detailsphoto;

    private Integer type;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getDetailsphoto() {
        return detailsphoto;
    }

    public void setDetailsphoto(String detailsphoto) {
        this.detailsphoto = detailsphoto == null ? null : detailsphoto.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
package com.computerGo.DTO;

import com.computerGo.pojo.Package;
import com.computerGo.pojo.Repertory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RepertoryDTOD
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/30 16:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoryPackageDTO {
    /*id*/
    private Integer rid;
    //浏览人数
    private String watched;
    //照片
    private List<String> photolist;
    /*名称*/
    private String title;
    /*价格*/
    private Double price;
    /*评价*/
    private Integer evaluation;
    /*套餐*/
    private List<Package> packageList;
    /*详情照片*/
    private List<String> detailsPhotoList;

    public void setRepertoryDTO(Repertory repertory){
        this.setRid(repertory.getRid());
        this.setPrice(repertory.getPrice());
        this.setTitle(repertory.getTitle());
        this.setPhotolist(changePhoto(repertory.getPhoto()));
        this.setDetailsPhotoList(changePhoto(repertory.getDetailsphoto()));
        this.setEvaluation(repertory.getEvaluation());
    }
    public List<String> changePhoto(String photo){
        String[] photoarr = photo.split(";");
        List<String> photolist = new ArrayList<>();
        for (String str : photoarr){
            photolist.add(str);
        }
        return photolist;
    }
}

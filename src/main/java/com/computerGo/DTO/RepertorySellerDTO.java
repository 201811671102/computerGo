package com.computerGo.DTO;

import com.computerGo.pojo.Repertory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RepertoryDTODD
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/30 17:30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepertorySellerDTO {
    /*id*/
    private Integer rid;
    //浏览人数
    private String watched;
    //照片
    private String photo;
    /*名称*/
    private String title;
    /*价格*/
    private Double price;
    /*库存*/
    private Integer number;

    public void setRepertoryDTO(Repertory repertory){
        this.setRid(repertory.getRid());
        this.setPrice(repertory.getPrice());
        this.setTitle(repertory.getTitle());
        this.setPhoto(changePhoto(repertory.getPhoto()));
        this.setNumber(repertory.getNumber());
    }
    public String changePhoto(String photo){
        String[] photoarr = photo.split(";");
        return photoarr[photoarr.length-1];
    }
}

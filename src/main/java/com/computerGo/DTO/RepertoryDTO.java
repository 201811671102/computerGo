package com.computerGo.DTO;


import com.computerGo.pojo.Repertory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RepertoryDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 18:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoryDTO {
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

    public void setRepertoryDTO(Repertory repertory){
        this.setRid(repertory.getRid());
        this.setPrice(repertory.getPrice());
        this.setTitle(repertory.getTitle());
        this.setPhoto(changePhoto(repertory.getPhoto()));
    }
    public String changePhoto(String photo){
        String[] photoarr = photo.split(";");
        return photoarr[photoarr.length-1];
    }
}

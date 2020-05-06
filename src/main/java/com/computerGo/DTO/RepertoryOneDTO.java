package com.computerGo.DTO;

import com.computerGo.pojo.Package;
import com.computerGo.pojo.Repertory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RepertoryOneDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/4 16:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepertoryOneDTO {
    /*id*/
    private Integer rid;
    //照片
    private List<String> photo;
    //详情照片
    private List<String> detailsphoto;
    /*名称*/
    private String title;
    /*价格*/
    private Double price;
    /*商品类型*/
    private Integer type;
    /*库存数量*/
    private Integer number;
    /*套餐*/
    private List<Package> packageList;

    public void SetRepertoryOneDTO(Repertory repertory){
        this.setRid(repertory.getRid());
        this.setPrice(repertory.getPrice());
        this.setTitle(repertory.getTitle());
        this.setPhoto(changePhoto(repertory.getPhoto(),photo));
        this.setDetailsphoto(changePhoto(repertory.getDetailsphoto(),detailsphoto));
        this.setNumber(repertory.getNumber());
        this.setType(repertory.getType());
    }

    public List<String> changePhoto(String photo,List<String> photolist){
        photolist = new ArrayList<>();
        String[] arr = photo.split(";");
        for (String str : arr){
            photolist.add(str);
        }
        return photolist;
    }
}

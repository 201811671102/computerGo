package com.computerGo.DTO;

import com.computerGo.pojo.Package;
import com.computerGo.pojo.Repertory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RepertoryDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 18:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepertoryDTO extends Repertory {
    //浏览人数
    private String watched;
    //照片列表
    private List<String> photolist;
    //套餐信息
    private List<Package> packageList;

    public void setRepertoryDTO(Repertory repertory){
        this.setRid(repertory.getRid());
        this.setEvaluation(repertory.getEvaluation());
        this.setPhotolist(this.changePhoto(repertory.getPhoto()));
        this.setPrice(repertory.getPrice());
        this.setTitle(repertory.getTitle());
    }

    public List<String> changePhoto(String photo){
        String[] photoarr = photo.split(";");
        List<String> list = new ArrayList<>();
        for (String str : photoarr){
            list.add(str);
        }
        return list;
    }
}

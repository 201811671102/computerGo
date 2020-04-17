package com.computerGo.DTO;

import com.computerGo.pojo.Package;
import com.computerGo.pojo.Repertory;
import com.computerGo.pojo.Theorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TheorderDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/16 12:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheorderDTO extends Theorder {
    private Repertory repertory;
    private Package newPackage;
    public void SetTheorderDTO(Theorder theorder){
        this.setOid(theorder.getOid());
        this.setOrdertime(theorder.getOrdertime());
        this.setPid(theorder.getPid());
        this.setRid(theorder.getRid());
        this.setNum(theorder.getNum());
        this.setState(theorder.getState());
        this.setUaddress(theorder.getUaddress());
    }
}

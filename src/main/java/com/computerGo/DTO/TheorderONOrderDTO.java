package com.computerGo.DTO;

import com.computerGo.pojo.Theorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TheorderONOrderDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/1 12:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheorderONOrderDTO {
    /*订单*/
    private Integer oid;
    /*名称*/
    private String title;
    /*套餐*/
    private String packagemessage;
    /*订单号*/
    private String ordernumber;


    public void SetTheorderDTO(Theorder theorder){
        this.setOid(theorder.getOid());
        this.setOrdernumber(theorder.getOrdernumber());
    }
}

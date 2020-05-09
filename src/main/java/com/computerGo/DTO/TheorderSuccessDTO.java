package com.computerGo.DTO;

import com.computerGo.pojo.Theorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TheorderSuccessDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/6 18:08
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheorderSuccessDTO {
    /*时间*/
    private String ordertime;
    /*数量*/
    private Integer num;
    /*名称*/
    private String title;
    /*套餐*/
    private String packagemessage;
    /*订单号*/
    private String ordernumber;

    public void SetTheorderDTO(Theorder theorder){
        this.setOrdertime(timeType(theorder.getOrdertime()));
        this.setNum(theorder.getNum());
        this.setOrdernumber(theorder.getOrdernumber());
    }

    public String timeType(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }
}

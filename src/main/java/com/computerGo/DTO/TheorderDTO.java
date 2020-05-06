package com.computerGo.DTO;

import com.computerGo.pojo.Theorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TheorderDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/16 12:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheorderDTO {
    /*订单*/
    private Integer oid;
    /*时间*/
    private String ordertime;
    /*数量*/
    private Integer num;
    /*套餐id*/
    private Integer rid;
    /*名称*/
    private String title;
    /*类型*/
    private String type;
    /*套餐*/
    private String packagemessage;
    /*订单号*/
    private String ordernumber;
    /*收货人姓名*/
    private String uname;
    /*收货人电话*/
    private String uphone;
    /*收货人地址*/
    private String uaddress;
    /*套餐id*/
    private Integer pid;
    /*评价*/
    private Integer evaluation;

    public void SetTheorderDTO(Theorder theorder){
        this.setOid(theorder.getOid());
        this.setOrdertime(timeType(theorder.getOrdertime()));
        this.setNum(theorder.getNum());
        this.setOrdernumber(theorder.getOrdernumber());
        this.setUaddress(theorder.getUaddress());
        this.setUname(theorder.getUname());
        this.setUphone(theorder.getUphone());
        this.setPid(theorder.getPid());
    }

    public String timeType(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }
}

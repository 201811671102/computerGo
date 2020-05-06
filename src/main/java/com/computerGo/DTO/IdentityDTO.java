package com.computerGo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName IdentityDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/1 0:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDTO {
    private Integer iid;

    private String uname;

    private String uphone;

    private String uaddress;
}

package com.computerGo.DTO;

import com.computerGo.pojo.UI;
import com.computerGo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDto
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 14:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends User {
    private Integer role;
    public void setUserDto(User user){
        this.setUid(user.getUid());
        this.setOpenid(user.getOpenid());
    }

}

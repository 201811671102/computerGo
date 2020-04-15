package com.computerGo.service;


import com.computerGo.pojo.Package;

import java.util.List;

public interface PackageService {
    /*增加套餐*/
    int insertPackage(Package newpackage)throws Exception;
    /*删除套餐*/
    int deleteByPid(Integer pid)throws Exception;
    /*修改套餐*/
    int updatePackage(Package newpackage)throws Exception;
    /*pid查询套餐*/
    Package selectByPid(Integer pid)throws Exception;
}

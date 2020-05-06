package com.computerGo.service.impl;

import com.computerGo.mapper.PackageMapper;
import com.computerGo.pojo.Package;
import com.computerGo.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PackageServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 13:44
 **/
@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageMapper packageMapper;

    @Override
    public int insertPackage(Package newpackage) throws Exception {
        return packageMapper.insertSelective(newpackage);
    }

    @Override
    public int deleteByPid(Integer pid) throws Exception {
        return packageMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int updatePackage(Package newpackage) throws Exception {
        return packageMapper.updateByPrimaryKeySelective(newpackage);
    }

    @Override
    public Package selectByPid(Integer pid) throws Exception {
        return packageMapper.selectByPrimaryKey(pid);
    }
}

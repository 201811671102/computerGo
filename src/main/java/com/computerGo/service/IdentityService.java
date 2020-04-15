package com.computerGo.service;

import com.computerGo.pojo.Identity;

public interface IdentityService {
    /*根据iid查询身份*/
    Identity selectByIid(Integer iid)throws Exception;
    /*根据iid删除身份*/
    int deleteByIid(Integer iid)throws Exception;
    /*插入身份*/
    int insertIdentity(Identity identity)throws Exception;
}

package com.office.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.office.model.system.SysUser;
import com.office.vo.system.RouterVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author chp
 * @since 2023-05-04
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);


    SysUser getUserByUserName(String username);

    Map<String, Object> getCurrentUser();
}

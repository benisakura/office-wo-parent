package com.office.auth.service.impl;

import com.office.auth.service.SysMenuService;
import com.office.auth.service.SysUserService;
import com.office.model.system.SysUser;
import com.office.security.custom.CustomUser;
import com.office.security.custom.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名进行查询
        SysUser sysUser=sysUserService.getUserByUserName(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }
        //根据userid查询用户操作权限数据
        List<String> userPermsList = sysMenuService.findUserPermsByUserId(sysUser.getId());
        //创建List集合，封装最终权限数据
        List<SimpleGrantedAuthority> authList=new ArrayList<>();
        for (String perm:userPermsList) {
            authList.add(new SimpleGrantedAuthority(perm));

        }
        return new CustomUser(sysUser, authList);
    }

}

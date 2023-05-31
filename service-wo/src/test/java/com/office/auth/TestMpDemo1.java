package com.office.auth;

import com.office.auth.mapper.SysRoleMapper;
import com.office.model.system.SysRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMpDemo1 {


    //注入
    @Autowired
    private SysRoleMapper mapper;

    //查询所有记录
    @Test
    public void getAll() {
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }


        //添加操作
        @Test
    public void add(){
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("角色管理员1");
            sysRole.setDescription("角色管理员1");
            sysRole.setRoleCode("role");
            int rows = mapper.insert(sysRole);
            System.out.println(rows);
            System.out.println(sysRole.getId());
        }

        //修改操作
    @Test
    public void update(){
        //根据id查询
        SysRole sysRole = mapper.selectById(10);
        //设置修改值
        sysRole.setRoleName("管理员111");
        //调用方法实现最终修改
        int i = mapper.updateById(sysRole);
        System.out.println(i);
    }

    //删除操作
    @Test
    public void deleteId(){
        int i = mapper.deleteById(10);

    }

}

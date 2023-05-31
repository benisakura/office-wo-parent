package com.office.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.office.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}

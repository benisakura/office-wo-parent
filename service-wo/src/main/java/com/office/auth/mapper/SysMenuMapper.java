package com.office.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.office.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 *
 * </p>
 *
 * @author chp
 * @since 2023-05-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);
}

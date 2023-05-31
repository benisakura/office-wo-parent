package com.office.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.office.model.system.SysMenu;
import com.office.vo.system.AssginMenuVo;
import com.office.vo.system.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author chp
 * @since 2023-05-06
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);

    List<SysMenu> findMenuByRoleId(Long roleId);

    void doAssign(AssginMenuVo assginMenuVo);

    List<String> findUserPermsByUserId(Long userId);

    List<RouterVo> findUserMenuListByUserId(Long userId);
}

package com.office.wechat.service;

import com.office.model.wechat.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.office.vo.wechat.MenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author chp
 * @since 2023-05-30
 */
public interface MenuService extends IService<Menu> {

    List<MenuVo> findMenuInfo();

    void syncMenu();

    void removeMenu();
}

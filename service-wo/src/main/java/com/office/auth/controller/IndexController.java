package com.office.auth.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.office.auth.service.SysMenuService;
import com.office.auth.service.SysUserService;
import com.office.common.config.exception.OfficeException;
import com.office.common.jwt.JwtHelper;
import com.office.common.result.Result;
import com.office.common.utils.MD5;
import com.office.model.system.SysUser;
import com.office.vo.system.LoginVo;
import com.office.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 后台登录登出
 * </p>
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")

public class IndexController {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    //login
    @PostMapping("login")
    public Result login(@RequestBody  LoginVo loginVo){
//        Map<String, Object> map = new HashMap<>();
//        map.put("token","admin-token");
//        return Result.ok(map);
    //1 获取输入用户名和密码
    //2 根据用户名查询数据库
        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(wrapper);

        //3 判断用户信息是否存在
        if(sysUser==null){
            throw new OfficeException(201,"用户不存在");
        }

        //4 判断密码
        //数据库密码（MD5加密）,获取页面密码进行加密后比较
        String password_db = sysUser.getPassword();
        String passsword_input = MD5.encrypt(loginVo.getPassword());
        if(!password_db.equals(passsword_input)){
            throw  new OfficeException(201,"密码错误");
        }
        //5 判断用户是否被禁用 1 可用 0禁用
        if(sysUser.getStatus()==0){
            throw  new OfficeException(201,"用户已被禁用");
        }
        //6 使用jwt根据用户id和用户名称生成token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        //7 返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);

    }

    //info
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        //1 从请求头获取用户信息，（获取请求头token字符串）
        String token = request.getHeader("token");
        //2 从token字符串获取用户id或者用户名称
        Long userId = JwtHelper.getUserId(token);
        //3 根据用户id查询数据库，把用户信息获取出来
        SysUser sysUser = sysUserService.getById(userId);
        //4 根据用户id获取用户可以操作菜单列表
        //查询数据库动态构建路由结构，进行显示
        List<RouterVo> routerList=sysMenuService.findUserMenuListByUserId(userId);
        //5 根据用户id获取用户可以操作按钮列表
       List<String> premsList = sysMenuService.findUserPermsByUserId(userId);
       //6 返回相应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
       //返回用户可以操作按钮
        map.put("buttons",premsList);
        //返回用户可以操作菜单
        map.put("routers",routerList);
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}

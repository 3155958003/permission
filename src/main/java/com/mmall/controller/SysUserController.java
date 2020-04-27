package com.mmall.controller;

import com.google.common.collect.Maps;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.param.UserParam;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysTreeService;
import com.mmall.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author hx
 * @create 2020-04-16 17:00
 *
 * 用户控制类
 */

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService ;

    @Resource
    private SysTreeService sysTreeService ;

    @Resource
    private SysRoleService sysRoleService ;

    /**
     * 当用户无权限访问，跳转页面
     * @return
     */
    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth(){
        return new ModelAndView("noAuth") ;
    }

    /**
     * 新增用户
     * @param param
     *              用户参数
     * @return
     *          返回值
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param){
        sysUserService.save(param);
        return JsonData.success() ;
    }

    /**
     * 更新用户
     * @param param
     *              用户参数
     * @return
     *         返回值
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param){
        sysUserService.update(param);
        return JsonData.success() ;
    }

    /**
     *
     * 根据部门查询用户并分页
     * @param deptId
     *              部门id
     * @param pageQuery
     *                 分页查询条件
     * @return
     *         返回值
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery){
        PageResult result = sysUserService.getPageByDeptId(deptId, pageQuery) ;
        return JsonData.success(result) ;
    }

    /**
     * 展示用户的权限
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId){
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map) ;
    }
}

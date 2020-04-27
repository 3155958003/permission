package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import com.mmall.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author hx
 * @create 2020-04-17 11:37
 *
 * 权限模块控制层
 */

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService ;

    @Resource
    private SysTreeService sysTreeService ;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl") ;
    }

    /**
     * 新增权限
     * @param param
     *              权限参数
     * @return
     *          返回值
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param){
        sysAclModuleService.save(param);
        return JsonData.success() ;
    }

    /**
     * 更新权限
     * @param param
     *             权限参数
     * @return
     *         返回值
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param){
        sysAclModuleService.update(param);
        return JsonData.success() ;
    }

    /**
     * 权限树展示
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        return JsonData.success(sysTreeService.aclModuleTree()) ;
    }

    /**
     * 根据id删除
     * @param id
     *          传入的id值
     * @return
     *          返回值
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id){
        sysAclModuleService.delete(id);
        return JsonData.success() ;
    }
}

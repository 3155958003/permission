package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.DeptLevelDto;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hx
 * @create 2020-04-14 11:52
 *
 * 部门控制层
 *
 */

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService ;

    @Resource
    private SysTreeService sysTreeService ;

    @RequestMapping("/dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept") ;
    }

    /**
     * 保存部门
     * @param param
     *              部门参数
     * @return
     *          返回值
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param){
        sysDeptService.save(param);
        return JsonData.success() ;
    }

    /**
     * 树结构
     * @return
     *         返回值
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree() ;

        return JsonData.success(dtoList) ;
    }

    /**
     * 更新部门
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param){
        sysDeptService.update(param);
        return JsonData.success() ;
    }

    /**
     * 根据id删除部门
     * @param id
     *          部门id
     * @return
     *          返回值
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id){
        sysDeptService.delete(id);
        return JsonData.success() ;
    }
}

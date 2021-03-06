package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author hx
 * @create 2020-04-18 21:28
 *
 *  权限点的服务层
 */

@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper ;

    @Resource
    private SysLogService sysLogService ;

    /**
     * 新增权限点参数
     * @param param
     *              传入的权限点参数
     */
    public void save(AclParam param){
        BeanValidator.check(param);
        if (checkExit(param.getAclModuleId(),param.getName(), param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }

        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark())
                .build() ;

        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());

        sysAclMapper.insertSelective(acl) ;

        sysLogService.saveAclLog(null, acl);

    }

    /**
     * 更新权限点参数
     * @param param
     *              传入需更新的权限点参数
     */
    public void update(AclParam param){
        BeanValidator.check(param);

        if (checkExit(param.getAclModuleId(),param.getName(), param.getId())){
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }

        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId()) ;
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark())
                .build() ;

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after) ;

        sysLogService.saveAclLog(before, after);

    }

    /**
     * 判断是否有重复的权限点
     * @param aclModule
     *                  权限模块
     * @param name
     *             权限点名称
     * @param id
     *           权限点id
     * @return
     *         返回值
     */
    public boolean checkExit(int aclModule, String name, Integer id){
        return sysAclMapper.countByNameAndAclModuleId(aclModule, name, id) > 0;
    }

    /**
     * 以时间随机生成Code值
     * @return
     *        返回值
     */
    public String generateCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    /**
     * 查询权限点且分页
     * @param aclModuleId
     *                  权限模块id
     * @param page
     *            分页数据
     * @return
     *        返回值
     */
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }
}

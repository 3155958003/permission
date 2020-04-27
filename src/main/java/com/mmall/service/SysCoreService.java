package com.mmall.service;

import com.google.common.collect.Lists;
import com.mmall.beans.CacheKeyConstants;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysUser;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hx
 * @create 2020-04-19 15:59
 *
 */

@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper ;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper ;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper ;

    @Resource
    private SysCacheService sysCacheService ;

    /**
     * 获取当前用户的权限点集合列表
     * @return
     *        返回值
     *
     */
    public List<SysAcl> getCurrentUserAclList(){
        int userId = RequestHolder.getCurrentUser().getId() ;

        return getUserAclList(userId) ;
    }

    /**
     *  权限模块下的权限点集合
     * @param roleId
     *              权限模块
     * @return
     *         返回值
     */
    public List<SysAcl> getRoleAclList(int roleId){
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId)) ;

        if (CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList() ;
        }

        return sysAclMapper.getByIdList(aclIdList) ;
    }

    /**
     * 根据用户查询权限点集合（用户权限点集合列表）
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    public List<SysAcl> getUserAclList(int userId){
        if (isSuperAdmin()){
            return sysAclMapper.getAll() ;
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId) ;

        if (CollectionUtils.isEmpty(userRoleIdList)){
            return Lists.newArrayList() ;
        }

        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList) ;
        if (CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList() ;
        }

        return sysAclMapper.getByIdList(userAclIdList) ;
    }

    /**
     * 判断是否是超级管理员
     * @return
     *          返回值
     */
    public boolean isSuperAdmin(){
        SysUser sysUser = RequestHolder.getCurrentUser() ;
        if (sysUser.getMail().contains("admin")){
           return true ;
        }
        return false ;
    }

    /**
     * 是否有权限进行访问
     * @param url
     *            传入的url地址
     * @return
     *        返回值
     */
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }

        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            // 权限点无效
            if (acl == null || acl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        if (!hasValidAcl) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前用户权限点的缓存
     * @return
     *      返回值
     */
    public List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}

package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author hx
 * @create 2020-04-17 12:11
 */

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList() ;

    private List<AclDto> aclList = Lists.newArrayList() ;

    /**
     * 构造权限模块层级
     * @param aclModule
     *                  权限模块
     * @return
     *         返回值
     */
    public static AclModuleLevelDto adapt(SysAclModule aclModule){
        AclModuleLevelDto dto = new AclModuleLevelDto() ;
        BeanUtils.copyProperties(aclModule, dto);

        return dto ;
    }

}

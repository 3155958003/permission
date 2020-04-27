package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hx
 * @create 2020-04-16 17:17
 *
 * 用户服务层
 */

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper ;

    @Resource
    private SysLogService sysLogService ;

    /**
     * 用户新增
     * @param param
     *              用户参数
     */
    public void save(UserParam param){
        BeanValidator.check(param);
        if (checkEmailExist(param.getMail(), param.getId())){
            throw new ParamException("该邮箱已被使用，请更换") ;
        }

        if (checkTelephoneExist(param.getTelephone(), param.getId())){
            throw new ParamException("该电话号码已被使用，请更换") ;
        }

        String password = PasswordUtil.randomPassword() ;
        password = "12345678" ;
        String encryptedPassword = MD5Util.encrypt(password) ;
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus())
                .remark(param.getRemark()).build() ;

        user.setOperator(RequestHolder.getCurrentUser().getUsername()) ;
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())) ;
        user.setOperateTime(new Date()) ;

        //  TODO: sendEmail

        //  保存用户
        sysUserMapper.insertSelective(user) ;

        sysLogService.saveUserLog(null, user);

    }

    /**
     * 用户更新
     * @param param
     *              用户参数
     */
    public void update(UserParam param){
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用,请更换");
        }
        if(checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用，请更换");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername())
                .telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(after);

        sysLogService.saveUserLog(before, after);
    }

    /**
     * 校验邮箱是否存在
     * @param mail
     *              邮箱
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    private boolean checkEmailExist(String mail, Integer userId){
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    /**
     * 校验电话是否被使用
     * @param telephone
     *                  电话号码
     * @param userId
     *              用户id
     * @return
     *          返回值
     */
    private boolean checkTelephoneExist(String telephone, Integer userId){
        return sysUserMapper.countByTelephone(telephone, userId) > 0 ;
    }

    /**
     * 根据传入的字符查询用户信息
     * @param keyword
     *                 传入的关键字
     * @return
     *          返回值
     */
    public SysUser findByKeyword(String keyword){
        return sysUserMapper.findByKeyword(keyword) ;
    }

    /**
     * 分页查询用户
     * @param deptId
     *             部门id
     * @param page
     *             页数
     * @return
     *         返回值
     */
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page){
        BeanValidator.check(page);

        int count = sysUserMapper.countByDeptId(deptId) ;

        if (count > 0){
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page) ;
            return PageResult.<SysUser>builder().total(count).data(list).build() ;
        }

        return PageResult.<SysUser>builder().build() ;
    }

    /**
     * 查询所有用户
     * @return
     *         返回所有用户
     */
    public List<SysUser> getAll(){
        return sysUserMapper.getAll() ;
    }
}

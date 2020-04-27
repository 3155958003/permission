package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hx
 * @create 2020-04-16 17:02
 *
 * 用户参数类
 */

@Getter
@Setter
@ToString
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 20, message = "用户名长度需在20字以内")
    private String username;

    @NotBlank(message = "电话号码不可以为空")
    @Length(min = 1, max = 13, message = "电话号码长度需为13个字符")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5, max = 50, message = "邮箱长度需在50个字符之内")
    private String mail;

    @NotNull(message = "必须指定用户所在的部门")
    private Integer deptId;

    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2,message = "用户状态不合法")
    private Integer status;

    @Length(min = 0, message = "用户备注长度须在200字以内")
    private String remark = "";
}

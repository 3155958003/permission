package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author hx
 * @create 2020-04-14 11:46
 *
 * 部门参数
 */

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id ;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 20, min = 2, message = "部门名称长度需在2-20个字符之间")
    private String name ;

    private Integer parentId = 0 ;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq ;

    @Length(max = 150, message = "备注长度需在150字以内")
    private String remark ;
}

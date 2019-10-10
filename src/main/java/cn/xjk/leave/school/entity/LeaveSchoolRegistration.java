package cn.xjk.leave.school.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author XuJiakai
 * @ClassName: LeaveScHoolRegistration
 * @Description:
 * @date 2019/9/18 12:48
 */
@Data
@TableName("leave_school_registration")
public class LeaveSchoolRegistration implements Serializable  {
    @NotBlank(message = "学号不能为空!")
    @TableId(value = "stu_no", type = IdType.INPUT)
    private String stuNo;

    @NotBlank(message = "姓名不能为空!")
    @TableField("stu_name")
    private String stuName;

    @NotBlank(message = "请选择班级!")
    @TableField("clazz_name")
    private String clazzName;

    /**
     * 0 false
     */
    @NotNull(message = "假期去向不能为空!")
    @TableField("go")
    private Integer go;

    @TableField("go_describe")
    private String goDescribe;

    @TableField("phone")
    private String phone;

    @TableField("go_time")
    private Date goTime;

    @TableField("back_time")
    private Date backTime;
}

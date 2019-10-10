package cn.xjk.leave.school.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author XuJiakai
 * @ClassName: AllStudent
 * @Description:
 * @date 2019/9/18 13:30
 */
@Data
@TableName("all_student")
public class AllStudent implements Serializable {
    @TableId(value = "stu_no",type = IdType.INPUT)
    private String stuNo;
    @TableField("stu_name")
    private String stuName;
    @TableField("clazz_name")
    private String clazzName;
}

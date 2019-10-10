package cn.xjk.leave.school.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author XuJiakai
 * @ClassName: AllClazz
 * @Description:
 * @date 2019/9/18 13:32
 */
@Data
@TableName("all_clazz")
public class AllClazz implements Serializable {
    @TableId(value = "clazz_name",type = IdType.INPUT)
    private String clazzName;
}

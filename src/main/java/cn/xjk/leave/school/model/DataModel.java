package cn.xjk.leave.school.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataModel extends BaseRowModel implements Comparable<DataModel> {
    @ExcelProperty(index = 0)
    private String stuNo;

    @ExcelProperty(index = 1)
    private String stuName;

    @ExcelProperty(index = 2)
    private String clazzName;

    @ExcelProperty(index = 3)
    private String go;

    @ExcelProperty(index = 4)
    private String goDescribe;

    @ExcelProperty(index = 5)
    private String phone;

    @ExcelProperty(index = 6, format = "yyyy-MM-dd")
    private Date goTime;

    @ExcelProperty(index = 7, format = "yyyy-MM-dd")
    private Date backTime;

    @Override
    public int compareTo(DataModel o) {
        return getStuNo().compareTo(o.getStuNo());
    }
}

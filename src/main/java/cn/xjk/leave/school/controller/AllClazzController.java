package cn.xjk.leave.school.controller;

import cn.xjk.leave.school.base.BaseController;
import cn.xjk.leave.school.base.Result;
import cn.xjk.leave.school.entity.AllClazz;
import cn.xjk.leave.school.mapper.AllClazzMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XuJiakai
 * @ClassName: AllClazzController
 * @Description:
 * @date 2019/9/18 13:43
 */
@RestController
@RequestMapping("clazz")
public class AllClazzController extends BaseController {

    @Autowired
    AllClazzMapper allClazzMapper;

    @GetMapping("list")
    public Result<List<AllClazz>> list() {
        QueryWrapper<AllClazz> wrapper = new QueryWrapper();
        List<AllClazz> allClazzes = allClazzMapper.selectList(wrapper);
        return this.success(allClazzes);
    }

    @PostMapping("add")
    public Result add(@RequestBody AllClazz allClazz) {
        if (StringUtils.isEmpty(allClazz.getClazzName())) {
            return this.failure("请输入正确的班级！");
        }
        try {
            allClazzMapper.insert(allClazz);
            return this.success();
        } catch (Exception e) {
            return this.failure("请务登记重复的班级！");
        }
    }

    @PostMapping("del")
    public Result del(@RequestBody AllClazz allClazz) {
        if (StringUtils.isEmpty(allClazz.getClazzName())) {
            return this.failure("请输入正确的班级！");
        }
        try {
            allClazzMapper.deleteById(allClazz.getClazzName());
            return this.success("删除成功！");
        } catch (Exception e) {
            return this.failure(e.getMessage());
        }
    }
}

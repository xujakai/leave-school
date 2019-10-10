package cn.xjk.leave.school.controller;

import cn.xjk.leave.school.base.BaseController;
import cn.xjk.leave.school.base.Result;
import cn.xjk.leave.school.entity.AllStudent;
import cn.xjk.leave.school.entity.LeaveSchoolRegistration;
import cn.xjk.leave.school.mapper.AllStudentMapper;
import cn.xjk.leave.school.mapper.LeaveSchoolRegistrationMapper;
import cn.xjk.leave.school.model.DataModel;
import cn.xjk.leave.school.service.ExcelService;
import cn.xjk.leave.school.service.LeaveSchoolRegistrationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuJiakai
 * @ClassName: LeaveSchoolRegistrationController
 * @Description:
 * @date 2019/9/18 13:44
 */
@Slf4j
@RestController
@RequestMapping("leave-school-registration")
public class LeaveSchoolRegistrationController extends BaseController {
    @Autowired
    LeaveSchoolRegistrationMapper leaveSchoolRegistrationMapper;

    @Autowired
    AllStudentMapper allStudentMapper;

    @Autowired
    LeaveSchoolRegistrationService leaveSchoolRegistrationService;

    @PostMapping("add")
    public Result add(@RequestBody @Valid LeaveSchoolRegistration leaveSchoolRegistration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        if (leaveSchoolRegistration.getGo() == 1) {
            if (!(StringUtils.isNotBlank(leaveSchoolRegistration.getPhone()) && leaveSchoolRegistration.getPhone().length() == 11)) {
                return this.failure("请输入正确的手机号！");
            }
            if (StringUtils.isBlank(leaveSchoolRegistration.getGoDescribe()) || leaveSchoolRegistration.getGoTime() == null || leaveSchoolRegistration.getBackTime() == null) {
                return this.failure("请补全信息！");
            }
        }

        try {
            AllStudent allStudent = new AllStudent();
            leaveSchoolRegistrationMapper.insert(leaveSchoolRegistration);
            try {
                BeanUtils.copyProperties(leaveSchoolRegistration, allStudent);
                allStudentMapper.insert(allStudent);
            } catch (Exception e) {
            }
            return this.success("登记成功！");
        } catch (Exception e) {
            return this.failure("请勿重复登记！");
        }
    }

    @GetMapping("list")
    public Result<IPage<LeaveSchoolRegistration>> list(@RequestParam("clazzName") String clazzName, @RequestParam("ps") Integer pageSize, @RequestParam("pi") Integer pageIndex) {
        Page page = new Page(pageIndex, pageSize);
        return this.success(leaveSchoolRegistrationService.find(clazzName, page));
    }

    @Autowired
    ExcelService excelService;

    @GetMapping("export")
    public void export(@RequestParam("clazz") String clazzName, HttpServletResponse response) {
        String fileName = "导出数据.xlsx";
        try {
            QueryWrapper<LeaveSchoolRegistration> queryWrapper = new QueryWrapper();
            if(StringUtils.isNotEmpty(clazzName)){
                queryWrapper.eq("clazz_name", clazzName);
            }
            List<LeaveSchoolRegistration> leaveSchoolRegistrations = leaveSchoolRegistrationMapper.selectList(queryWrapper);
            List<DataModel> collect = leaveSchoolRegistrations.stream().map(e -> new DataModel(e.getStuNo(), e.getStuName(), e.getClazzName(), e.getGo().equals(0) ? "否" : "是", e.getGoDescribe(), e.getPhone(), e.getGoTime(), e.getBackTime())).sorted().collect(Collectors.toList());
            try (OutputStream out = new ByteArrayOutputStream()) {
                MultipartFile file = excelService.create(out, collect, "sheet1");
                if (file != null) {
                    response.setHeader("Content-Disposition", "attachment; filename* = UTF-8\"" + URLEncoder.encode(fileName) + "\"");
                    response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Length", String.valueOf(file.getSize()));
                    try {
                        IOUtils.copy(file.getInputStream(), response.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException("下载文件失败" + e);
                    }
                }
            }
        } catch (Exception ee) {
        }
    }
}

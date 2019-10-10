package cn.xjk.leave.school.service.impl;

import cn.xjk.leave.school.entity.LeaveSchoolRegistration;
import cn.xjk.leave.school.mapper.LeaveSchoolRegistrationMapper;
import cn.xjk.leave.school.service.LeaveSchoolRegistrationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuJiakai
 * @ClassName: LeaveSchoolRegistrationServiceImpl
 * @Description:
 * @date 2019/9/19 10:33
 */
@Service
public class LeaveSchoolRegistrationServiceImpl implements LeaveSchoolRegistrationService {
    @Autowired
    LeaveSchoolRegistrationMapper leaveSchoolRegistrationMapper;

    @Override
    public IPage<LeaveSchoolRegistration> find(String clazzName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(clazzName)) {
            queryWrapper.eq("clazz_name", clazzName);
        }
        return null;
    }

    @Override
    public IPage<LeaveSchoolRegistration> find(String clazzName, Page page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(clazzName)) {
            queryWrapper.eq("clazz_name", clazzName);
        }
        page.setAsc("stu_no");
        return leaveSchoolRegistrationMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<LeaveSchoolRegistration> list(String clazzName) {
        return null;
    }
}

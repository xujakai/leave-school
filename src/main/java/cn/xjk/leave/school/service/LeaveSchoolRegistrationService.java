package cn.xjk.leave.school.service;

import cn.xjk.leave.school.entity.LeaveSchoolRegistration;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author XuJiakai
 * @ClassName: LeaveSchoolRegistrationService
 * @Description:
 * @date 2019/9/19 10:30
 */
public interface LeaveSchoolRegistrationService {
    IPage<LeaveSchoolRegistration> find(String clazzName);

    IPage<LeaveSchoolRegistration> find(String clazzName, Page page);

    List<LeaveSchoolRegistration> list(String clazzName);
}

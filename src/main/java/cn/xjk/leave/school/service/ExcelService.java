package cn.xjk.leave.school.service;


import cn.xjk.leave.school.model.DataModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 张骥
 * @date 2019/6/5
 * Description:
 */
public interface ExcelService {
    MultipartFile create(OutputStream out, Stream<DataModel> stream, String keyword) throws IOException;
    MultipartFile create(OutputStream out, List<DataModel> datas, String keyword) throws IOException;

}

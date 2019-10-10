package cn.xjk.leave.school.service.impl;

import cn.xjk.leave.school.configuration.ModelCache;
import cn.xjk.leave.school.model.ByteArrayMultipartFile;
import cn.xjk.leave.school.model.DataModel;
import cn.xjk.leave.school.service.ExcelService;
import cn.xjk.leave.school.util.MyExcelBuilderImpl;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilderImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.signature.qual.SignatureUnknown;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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
@Service
@Slf4j
@SuppressWarnings("all")
public class ExcelServiceImpl implements ExcelService {

    @Override
    public MultipartFile create(OutputStream out, Stream<DataModel> stream, String keyword) throws IOException {
        MyExcelBuilderImpl myExcelBuilder = new MyExcelBuilderImpl(ModelCache.MODEL.getModel(), out, ExcelTypeEnum.XLSX, false);
        Sheet sheet = new Sheet(1, 3, DataModel.class, keyword, null);
        sheet.setStartRow(1);

        myExcelBuilder.addContent(stream, sheet);
        try {
            out.flush();
        } catch (IOException e) {
            log.error("out flush error", e);
        }
        myExcelBuilder.finish();

        if (out instanceof ByteArrayOutputStream) {

            try (ByteArrayOutputStream bout = (ByteArrayOutputStream) out) {
                MultipartFile file = new ByteArrayMultipartFile("file",
                        "导出数据" + System.currentTimeMillis() + ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        bout.toByteArray());
                log.info("导出数据完成");
                return file;
            }
        } else {
            log.error("流格式错误");
        }
        return null;
    }

    @Override
    public MultipartFile create(OutputStream out, List<DataModel> datas, String keyword) throws IOException {
        ExcelBuilderImpl myExcelBuilder = new ExcelBuilderImpl(ModelCache.MODEL.getModel(), out, ExcelTypeEnum.XLSX, false);
        Sheet sheet = new Sheet(1, 3, DataModel.class, keyword, null);
        sheet.setStartRow(1);
        myExcelBuilder.addContent(datas, sheet);
        try {
            out.flush();
        } catch (IOException e) {
            log.error("out flush error", e);
        }
        myExcelBuilder.finish();
        if (out instanceof ByteArrayOutputStream) {
            try (ByteArrayOutputStream bout = (ByteArrayOutputStream) out) {
                MultipartFile file = new ByteArrayMultipartFile("file",
                        "导出数据" + System.currentTimeMillis() + ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        bout.toByteArray());
                log.info("导出数据完成");
                return file;
            }
        } else {
            log.error("流格式错误");
        }
        return null;
    }
}

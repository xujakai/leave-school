package cn.xjk.leave.school.util;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.POITempFile;
import com.alibaba.excel.util.TypeUtil;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.ExcelBuilder;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 张骥
 * @date 2019/6/4
 * Description:
 */
@Slf4j
public class MyExcelBuilderImpl implements ExcelBuilder {

    private WriteContext context;
    private CellStyle defaultCellStyle;


    public MyExcelBuilderImpl(InputStream templateInputStream,
                              OutputStream out,
                              ExcelTypeEnum excelType,
                              boolean needHead) {
        try {
            //初始化时候创建临时缓存目录，用于规避POI在并发写bug
            POITempFile.createPOIFilesDirectory();
            context = new WriteContext(templateInputStream, out, excelType, needHead);
            Workbook workbook = context.getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
            cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
            cellStyle.setBorderTop(BorderStyle.THIN);//上边框
            cellStyle.setBorderRight(BorderStyle.THIN);//右边框
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            defaultCellStyle = cellStyle;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addContent(Stream<? extends BaseRowModel> data, Sheet sheetParam) {
        context.currentSheet(sheetParam);
        addContent(data, sheetParam.getStartRow());
    }

    public void addContent(Stream<? extends BaseRowModel> data, int startRow) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        final int row = getCurentRow(startRow);

        data.forEach(model -> {
            try {
                model.setCellStyleMap(IntStream.range(0,15).boxed().collect(Collectors.toMap(i->i,i->defaultCellStyle)));
                addOneRowOfDataToExcel(model, row + atomicInteger.getAndIncrement() + 1);

            } catch (NullPointerException e) {
                log.error("insert error", e);
            }
        });
    }

    private int getCurentRow(int start) {
        int rowNum = context.getCurrentSheet().getLastRowNum();
        if (rowNum == 0) {
            Row row = context.getCurrentSheet().getRow(0);
            if (row == null) {
                if (context.getExcelHeadProperty() == null || !context.needHead()) {
                    rowNum = -1;
                }
            }
        }
        if (rowNum < start) {
            rowNum = start;
        }
        return rowNum;
    }

    @Override
    public void addContent(List data, int startRow) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        int rowNum = getCurentRow(startRow);

        for (int i = 0; i < data.size(); i++) {
            int n = i + rowNum + 1;
            addOneRowOfDataToExcel(data.get(i), n);
        }
    }

    @Override
    public void addContent(List data, Sheet sheetParam) {
        context.currentSheet(sheetParam);
        addContent(data, sheetParam.getStartRow());
    }

    @Override
    public void addContent(List data, Sheet sheetParam, Table table) {
        context.currentSheet(sheetParam);
        context.currentTable(table);
        addContent(data, sheetParam.getStartRow());
    }

    @Override
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        context.getCurrentSheet().addMergedRegion(cra);
    }

    @Override
    public void finish() {
        try {
            context.getWorkbook().write(context.getOutputStream());
            context.getWorkbook().close();
        } catch (IOException e) {
            throw new ExcelGenerateException("IO error", e);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        for (int i = 0; i < oneRowData.size(); i++) {
            Object cellValue = oneRowData.get(i);
            WorkBookUtil.createCell(row, i, context.getCurrentContentStyle(), cellValue, TypeUtil.isNum(cellValue));
        }
    }


    private void addJavaObjectToExcel(Object oneRowData, Row row) {
        int i = 0;
        BeanMap beanMap = BeanMap.create(oneRowData);
        for (ExcelColumnProperty excelHeadProperty : context.getExcelHeadProperty().getColumnPropertyList()) {
            BaseRowModel baseRowModel = (BaseRowModel) oneRowData;
            String cellValue = TypeUtil.getFieldStringValue(beanMap, excelHeadProperty.getField().getName(),
                    excelHeadProperty.getFormat());

            CellStyle cellStyle = baseRowModel.getStyle(i) != null ? baseRowModel.getStyle(i)
                    : context.getCurrentContentStyle();
            WorkBookUtil.createCell(row, i, cellStyle, cellValue, TypeUtil.isNum(excelHeadProperty.getField()));
            i++;
        }

    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n) {

        Row row = WorkBookUtil.createRow(context.getCurrentSheet(), n);

        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List) oneRowData, row);
        } else {
            addJavaObjectToExcel(oneRowData, row);
        }
    }
}

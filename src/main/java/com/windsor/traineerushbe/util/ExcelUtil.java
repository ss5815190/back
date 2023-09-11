package com.windsor.traineerushbe.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
public class ExcelUtil {


    public static void exportExcel(List<?> list, String title, String sheetName, List<ExcelExportEntity> excelParams, String filePath, boolean isCreateHeader) {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, excelParams, filePath, exportParams);
    }

    private static void defaultExport(List<?> list, List<ExcelExportEntity> excelParams, String filePath, ExportParams exportParams) {
        OutputStream outStream = null;
        try {

            outStream = new FileOutputStream(filePath);
            Workbook workbook =  ExcelExportUtil.exportExcel(exportParams, excelParams, list);
            workbook.write(outStream);
        }catch (IOException e) {
            log.error("Create excel by template fail : {}", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

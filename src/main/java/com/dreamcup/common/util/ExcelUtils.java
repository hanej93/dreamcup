package com.dreamcup.common.util;

import com.dreamcup.common.annotation.ExcelColumn;
import com.dreamcup.common.vo.ExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtils {

    @Data
    public static class ExcelDownloadVo<T extends ExcelVo> {
        private String fileName;
        private String sheetName;
        private List<T> data;
        private List<Map<String, Object>> excelData = new ArrayList<>();
        private List<String> headerNames = new ArrayList<>();

        public ExcelDownloadVo(String fileName, String sheetName, List<T> data) {
            this.fileName = fileName;
            this.sheetName = sheetName;
            this.data = data;
            updateHeaderNames();
            updateExcelData();
        }

        private void updateHeaderNames() {
            if (data == null | data.isEmpty()) {
                return;
            }
            headerNames = ExcelUtils.getExcelHeaderNames(data.get(0).getClass());
        }

        private void updateExcelData() {
            if (data == null | data.isEmpty()) {
                return;
            }
            excelData = data.stream()
                    .map(ConvertUtils::convertDtoToMap)
                    .collect(Collectors.toList());
        }
    }

    public static <T extends ExcelVo> void downloadExcel(HttpServletResponse response, ExcelDownloadVo<T> excelDownloadVo) {
        log.info("ExcelUtils.downloadExcel excelDownloadVo: {}_{}", excelDownloadVo.getFileName(), excelDownloadVo.getSheetName());
        try {
            String encodedFileName = URLEncoder.encode(excelDownloadVo.getFileName(), StandardCharsets.UTF_8.toString());
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 엑셀 파일 출력
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             OutputStream os = response.getOutputStream()) {

            // 숫자 포맷(천단위로 콤마) 적용
            CellStyle numberStyle = setNumberStyle(workbook);
            XSSFSheet sheet = workbook.createSheet(excelDownloadVo.getSheetName());

            // 헤더 세팅
            int rownum = 0;
            Row headerRow = sheet.createRow(rownum++);
            List<String> excelHeaderNames = excelDownloadVo.getHeaderNames();

            for (int i = 0; i < excelHeaderNames.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(excelHeaderNames.get(i));
            }

            // 바디 세팅
            List<Map<String, Object>> excelData = excelDownloadVo.getExcelData();
            int totalCount = excelData.size();
            int count = 0;
            for (Map<String, Object> data : excelData) {
                ++count;
                if (count % 100 == 0) {
                    log.info("ExcelUtils.downloadExcel totalCount: {} / count: {}", totalCount, count);
                }
                Row row = sheet.createRow(rownum++);
                int cellnum = 0;
                for (String key : data.keySet()) {
                    Cell cell = row.createCell(cellnum++);
                    setCellValue(cell, data.get(key));
                    if (data.get(key) instanceof Number) {
                        cell.setCellStyle(numberStyle);
                    }
                }
            }

            // 각 열에 대해 셀 폭 자동 조정
            int numberOfColumns = excelHeaderNames.size();
            for (int i = 0; i < numberOfColumns; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(os);
        } catch (IOException e) {
            log.error("ExcelUtils.downloadExcel IOException", e);
            throw new RuntimeException(e);
        }
    }

    private static CellStyle setNumberStyle(Workbook workbook) {
        CellStyle numberStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        numberStyle.setDataFormat(format.getFormat("#,##0")); // 숫자 포맷
        return numberStyle;
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value != null) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue("");
        }
    }

    public static List<String> getExcelHeaderNames(Class<? extends ExcelVo> clazz) {
        List<String> excelHeaderNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(s -> s.isAnnotationPresent(ExcelColumn.class))
                .map(s -> s.getAnnotation(ExcelColumn.class).headerName())
                .collect(Collectors.toList());

        if (excelHeaderNames.isEmpty()) {
            log.warn("헤더 이름이 설정되어 있지 않습니다.");
        }
        return excelHeaderNames;
    }
}

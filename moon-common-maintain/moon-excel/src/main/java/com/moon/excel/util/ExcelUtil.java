package com.moon.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.StringUtils;
import com.moon.excel.config.DataHandler;

import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static <T> void importExcel(String fileStream, String sheetName, Class<T> dataClass, DataHandler<T> dataHandler) {
        int maxDealCount = dataHandler.getMaxDealCount();
        List<T> cachedDataList = new ArrayList<>();
        AnalysisEventListener<T> listener = new AnalysisEventListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext analysisContext) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= maxDealCount) {
                    try {
                        dataHandler.dealExcelRows(analysisContext.readSheetHolder().getSheetName(), cachedDataList);
                    } finally {
                        cachedDataList.clear();
                    }
                }
            }

            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {
                dataHandler.onException(exception);
                throw new ExcelAnalysisStopException(String.format("ExcelUtil importExcel onException：%s", exception));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (cachedDataList.isEmpty()) {
                    return;
                }
                try {
                    dataHandler.dealExcelRows(analysisContext.readSheetHolder().getSheetName(), cachedDataList);
                } finally {
                    cachedDataList.clear();
                }
            }
        };
        ExcelReader excelReader = EasyExcel.read(fileStream, dataClass, listener).build();
        if (StringUtils.isEmpty(sheetName)) {
            excelReader.readAll();
        } else {
            String[] sheetNames = sheetName.split(",");
            for (String oneSheetName : sheetNames) {
                ReadSheet sheet = EasyExcel.readSheet(oneSheetName).build();
                excelReader.read(sheet);
            }
        }
        excelReader.finish();
    }
}

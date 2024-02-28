package com.moon.excel.test;

import com.moon.excel.config.DataHandler;
import com.moon.excel.util.ExcelUtil;

import java.util.List;

public class TestImport {

    public static void main(String[] args) {

        String inputStream = "D:\\RepoDec\\Moon\\Moon\\moon-common-maintain\\moon-excel\\src\\main\\resources\\person.xls";
        ExcelUtil.importExcel(inputStream, "", Person.class,
                new DataHandler<Person>() {
                    @Override
                    public Integer getMaxDealCount() {
                        return 5;
                    }

                    @Override
                    public void dealExcelRows(String SheetName, List<Person> rows) {
                        System.out.println("TestImport dealExcelRows");
                        rows.forEach(person -> System.out.println(person.toString()));
                        System.out.println("TestImport size:");
                        System.out.println(rows.size());
                    }

                    @Override
                    public void onException(Exception e) {
                        // do something
                    }
                }

        );
    }
}

package com.moon.excel.config;

import java.util.List;

public interface DataHandler<T> {

    /**
     * max deal count
     * @return
     */
    default Integer getMaxDealCount() {
        return 100;
    }

    /**
     * read rows
     *
     * @param SheetName
     * @param rows
     */
    void dealExcelRows(String SheetName, List<T> rows);

    /**
     * exception
     */
    void onException(Exception e) throws Exception;

}

package com.moon.core.web.page;


import lombok.Data;

@Data
public class PageDomain {
    private Integer pageNum;
    private Integer pageSize;
    private String orderByColumn;
    private String isAsc = "asc";
}

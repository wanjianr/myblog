package com.douye.myblog.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;  // 搜索框中输入的关键词
    private String sort;    //
    private Long time;
    private String tag;     // 问题的标签
    private Integer offset;   // 当前页码偏移量
    private Integer size;   // 一页中显示问题的条数
}

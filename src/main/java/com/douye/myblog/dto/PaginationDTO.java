package com.douye.myblog.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    // 这里改为泛型接收QuestionDTO和NotifictionDTO
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.page = page;
        this.totalPage = totalPage;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page-i > 0) pages.add(0,page-i);
            if (page+i <= totalPage) pages.add(page+i);
        }

        // “首页”按钮是否显示
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // “尾页”按钮是否显示
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

        // “上一页”按钮是否显示
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // “下一页”按钮是否显示
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
    }
}

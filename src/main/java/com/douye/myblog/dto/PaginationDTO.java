package com.douye.myblog.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.page = page;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page-i > 0) pages.add(0,page-i);
            if (page+i < totalPage) pages.add(page+i);
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

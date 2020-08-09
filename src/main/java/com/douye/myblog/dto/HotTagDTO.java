package com.douye.myblog.dto;

import lombok.Data;

@Data
public class HotTagDTO implements Comparable{
    private String tag;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO)o).getPriority();
    }
}

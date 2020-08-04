package com.douye.myblog.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;   // 回复用户的id
    private String notifierName; // 回复用户的用户名
    private String outerTitle;  // 所回复问题的标题
    private Long outerId;    //
    private String typeName;
    private Integer type;
}

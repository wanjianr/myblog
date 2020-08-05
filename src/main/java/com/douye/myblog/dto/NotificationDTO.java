package com.douye.myblog.dto;

import lombok.Data;

/*
       相对Notification增加了typeName字段，避免多次查询
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;   // 回复用户的id
    private String notifierName; // 回复用户的用户名
    private String outerTitle;  // 所回复问题的标题
    private Long outerId;    //
    private String typeName;  // 类型名称（“回复了问题”，“回复了评论”）
    private Integer type;
}

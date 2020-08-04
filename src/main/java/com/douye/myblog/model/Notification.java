package com.douye.myblog.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifier;  // 发送通知账户的id，即回复问题的用户id
    private Long receiver;  // 接收通知用户的id，即所回复问题发起人的id
    private Long outerId;   // 所回复的 问题id / 评论id
    private Integer type;   // 指明该回复是评论还是解答
    private Long gmtCreate;  // 创建时间
    private Integer status;  // 标记该通知是否已读
    private String notifierName;
    private String outerTitle;
}
